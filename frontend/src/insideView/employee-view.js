import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { jwtDecode } from 'jwt-decode';
import '../styles/office-view.css'; // Make sure the path is correct

const EmployeeView = () => {
    const { employeeId } = useParams();
    const navigate = useNavigate();
    const [employee, setEmployee] = useState(null);
    const [office, setOffice] = useState([]);
    const [userRoles, setUserRoles] = useState([]);
    const [userId, setUserId] = useState(null);
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
        const decodedUserId = decodedToken.userId;
    
        if (decodedUserId !== userId) {
            setUserId(decodedUserId);
            updateUserRoles(decodedToken);
        }
    
        const fetchOfficeDetails = async () => {
            try {
                const employeeResponse = await axios.get(`http://localhost:8082/employee/findById/${employeeId}`, {
                    headers: { Authorization: `Bearer ${token}` }
                });
                setEmployee(employeeResponse.data);
    
                const officeResponse = await axios.get(`http://localhost:8082/office/${employeeResponse.data.officeID}`, {
                    headers: { Authorization: `Bearer ${token}` }
                });
                setOffice(officeResponse.data);
    
            } catch (error) {
                console.error('Error fetching details:', error);
                navigate(-1);
            }
        };
    
        fetchOfficeDetails();
    }, [employeeId, token, navigate, userId, userRoles]); // Removed 'officeId' and 'office' from dependencies
    

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


    const handleLogout = () => {
        localStorage.removeItem('token');
        navigate('/login');
      };
    
    return (
        <div>
    {employee ? (
        <>
            <h1>Name: {employee.name}</h1>
            <h1>Type: {employee.employeeType}</h1>
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
                        </div>
                        <div>
                               
                               <button className="office-button update-button" onClick={() => navigate(`/update-order/${order.id}`)}>Update</button>
                               
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
                    <button onClick={() => navigate(`/office-view/${office.id}`)}>Back to Office</button>
                )}
                <button onClick={() => navigate(`/create-order/${office.id}`)}>Create Order</button>
                <button onClick={handleLogout} className="logout-button">Logout</button>
            </div>

    );
};


export default EmployeeView;