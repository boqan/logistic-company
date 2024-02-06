import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { jwtDecode } from 'jwt-decode';
import '../styles/office-view.css'; // Make sure the path is correct

const OfficaManagerView = () => {
    const { officeId } = useParams();
    const navigate = useNavigate();
    const [office, setOffice] = useState(null);
    const [userRoles, setUserRoles] = useState([]);
    const [userId, setUserId] = useState(null);
    const [companyId, setCompanyId] = useState(null);
    const token = localStorage.getItem('token');
    const updateUserRoles = (decodedToken) => {
        const decodedRoles = decodedToken.roles ? decodedToken.roles.split(',') : [];
        setUserRoles(decodedRoles);
    };

    useEffect(() => {

        if (!token) {
            navigate('/login');
            return;
        }

        const decodedToken = jwtDecode(token);
        const decodedRoles = decodedToken.roles ? decodedToken.roles.split(',') : [];
        const decodedUserId = decodedToken.userId;

        if (decodedUserId !== userId) {
            setUserId(decodedUserId);
            updateUserRoles(decodedToken)
        }

        
        if (decodedRoles.includes('ADMIN') || (decodedRoles.includes('COMPANY_MANAGER'))) {
            const fetchOfficeDetails = async () => {
                try {
                    const response = await axios.get(`http://localhost:8082/office/${officeId}`, {
                        headers: { Authorization: `Bearer ${token}` }
                    });
                    setCompanyId(response.data.companyId);
                    if(decodedRoles.includes('COMPANY_MANAGER') && response.data.companyId===decodedUserId){
                        setOffice(response.data);
                    }
                    else if (decodedRoles.includes('ADMIN')){
                        setOffice(response.data);
                    }
                    else{
                        navigate(-1);
                    }

                } catch (error) {
                    console.error('Error fetching company details:', error);
                    navigate(-1);
                }
            };
            fetchOfficeDetails();
        }
        else if (decodedUserId.toString() === officeId) {
            const fetchOfficeDetails = async () => {
                try {
                    const response = await axios.get(`http://localhost:8082/office/${officeId}`, {
                        headers: { Authorization: `Bearer ${token}` }
                    });
                    setOffice(response.data);
                } catch (error) {
                    console.error('Error fetching company details:', error);
                    navigate(-1);
                }
            };
            fetchOfficeDetails();
        }
        
    }, [officeId, token, navigate, userId, userRoles,companyId]); // Include userId and userRoles in dependencies

    const deleteClient = async (clientId) => {
        try {
            await axios.delete(`http://localhost:8082/client/${clientId}`, {
                headers: { Authorization: `Bearer ${token}` },
            });
            
            // Update state to reflect the deletion of the client
            setOffice(prevOffice => {
                const updatedClients = prevOffice.clients.filter(client => client.id !== clientId);
                return { ...prevOffice, clients: updatedClients };
            });
        } catch (error) {
            console.error('Error deleting client:', error);
        }
    };

    const deleteOrder = async (orderId) => {
        try {
            await axios.delete(`http://localhost:8082/orders/${orderId}`, {
                headers: { Authorization: `Bearer ${token}` },
            });
            
            // Update state to reflect the deletion of the client
            setOffice(prevOffice => {
                const updatedOrders = prevOffice.orders.filter(order => order.id !== orderId);
                return { ...prevOffice, orders: updatedOrders };
            });
        } catch (error) {
            console.error('Error deleting client:', error);
        }
    };
    
    

      const deleteEmployee = async (employeeId) => {
        try {
            await axios.delete(`http://localhost:8082/employee/delete/${employeeId}`, {
                headers: { Authorization: `Bearer ${token}` },
            });
            // Assuming 'office' has an 'employees' array that needs updating
            setOffice(prevOffice => {
                const updatedEmployees = prevOffice.employees.filter(employee => employee.id !== employeeId);
                return { ...prevOffice, employees: updatedEmployees };
            });
        } catch (error) {
            console.error('Error deleting employee:', error); // Corrected the error message to refer to an employee instead of an office
        }
    };

    const handleLogout = () => {
        localStorage.removeItem('token');
        navigate('/login');
      };
    
    return (
        <div>
    {office ? (
        <>
            <h1>Office: {office.address}</h1>
            <h2>Employees:</h2>
            <div className="employee-list">
                {office.employees && office.employees.map(employee => (
                    <div className="employee-box" key={employee.id}> {/* Ensure unique key for each employee */}
                        <div className="employee-header">
                            <div className="employee-details">
                                <h2>Name:  {employee.name}</h2>
                                <h2>Salary:  {employee.salary}</h2>
                                <h2>Type:  {employee.employeeType}</h2>
                            </div>                              
                            <div>
                                <button className="view-button"  onClick={() => navigate(`/employee-view/${employee.id}`)}> View </button>        
                                                              
                                <button className="office-button update-button"  onClick={() => navigate(`/update-employee/${employee.id}`)}>Update   </button>
                                <button className="office-button delete-button" onClick={() => deleteEmployee(employee.id)}>Delete</button>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
            <h2>Clients: </h2>
            <div className="employee-list">
                {office.clients && office.clients.map(client => (
                    <div className="employee-box" key={client.id}> {/* Ensure unique key for each client */}
                        <div className="employee-header">
                            <div className="employee-details">
                                <h2>Name:  {client.name}</h2>
                                <h2>ID:  {client.id}</h2>
                            </div>                                
                            <div>
                                <button className="view-button" onClick={() => navigate(`/client-view/${client.id}`)}> View </button>
                                <button className="office-button update-button" onClick={() => navigate(`/update-client/${client.id}`)}>Update</button>
                                <button className="office-button delete-button" onClick={() => deleteClient(client.id)}>Delete</button> {/* Corrected to use a hypothetical deleteClient function */}
                            </div>
                        </div>
                    </div>
                ))}
            </div>
            <h2>Orders: </h2>
            <div className="employee-list">
                {office.orders && office.orders.map(order => (
                    <div className="employee-box" key={order.id}> {/* Ensure unique key for each client */}
                        <div className="employee-header">
                            <div className="employee-details">
                                <h2>ID:  {order.id}</h2>
                                <h2>Status:  {order.status}</h2>
                                <h2>Delivery Type:  {order.deliveryType}</h2>
                                <h2>Reciever Address:  {order.receiverAddress}</h2>
                                <h2>Weight:  {order.weight}</h2>
                                <h2>Sender:  {order.sender}</h2>
                                <h2>Receiver:  {order.receiver}</h2>

                            </div>                                
                            <div>
                               
                                <button className="office-button update-button" onClick={() => navigate(`/update-order/${order.id}`)}>Update</button>
                                <button className="office-button delete-button" onClick={() => deleteOrder(order.id)}>Delete</button> {/* Corrected to use a hypothetical deleteClient function */}
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </>
        ) : (
            <p>Loading office details...</p>
        )}
                {(userRoles.includes('ADMIN') || userRoles.includes('COMPANY_MANAGER')) && (
                    <button onClick={() => navigate(`/company-view/${companyId}`)}>Back to Offices</button>
                )}
                <button onClick={() => navigate(`/add-employee/${officeId}`)}>Add Employee</button>
                <button onClick={() => navigate(`/add-client/${officeId}`)}>Add Client</button>
                <button onClick={() => navigate(`/create-order/${officeId}`)}>Create Order</button>
                <button onClick={handleLogout} className="logout-button">Logout</button>
            </div>

    );
};


export default OfficaManagerView;