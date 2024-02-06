import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { jwtDecode } from 'jwt-decode';
import '../styles/office-view.css'; // Make sure the path is correct

const ClientView = () => {
    const { clientId } = useParams();
    const navigate = useNavigate();
    const [client, setClient] = useState(null);
    const [ordersReceiver, setOrdersReceiver] = useState([]);
    const [ordersSender, setOrdersSender] = useState([]);
    const [userRoles, setUserRoles] = useState([]);
    const [userId, setUserId] = useState(null);
    const [officeId, setOfficeId] = useState(null);
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
            updateUserRoles(decodedToken);
        }
    
        const fetchOfficeDetails = async () => {
            try {
                const response = await axios.get(`http://localhost:8082/client/${clientId}`, {
                    headers: { Authorization: `Bearer ${token}` }
                });
                setOfficeId(response.data.officeId);
                setClient(response.data);
    
                const receiveOrders = await axios.get(`http://localhost:8082/office/${response.data.officeId}/client_orders_receiver/${clientId}`, {
                    headers: { Authorization: `Bearer ${token}` }
                });
                setOrdersReceiver(receiveOrders.data);

                const sendOrders = await axios.get(`http://localhost:8082/office/${response.data.officeId}/client_orders_sender/${clientId}`, {
                    headers: { Authorization: `Bearer ${token}` }
                });
                setOrdersSender(sendOrders.data);
                

            } catch (error) {
                console.error('Error fetching client details:', error);
                navigate(-1);
            }
        };
        fetchOfficeDetails();
    }, [clientId, token, navigate, userId, userRoles, officeId]); // Removed 'orders' from dependencies
    


    const handleLogout = () => {
        localStorage.removeItem('token');
        navigate('/login');
      };
    
    return (
        <div>
    {client ? (
        <>
            <h1>Name: {client.name}</h1>
            <h1>ID: {client.id}</h1>
            <h2>Recieve: </h2>
            <div className="employee-list">
                {ordersReceiver && ordersReceiver.map(order => (
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
                    </div>
                ))}
            </div>
            <h2>Send: </h2>
            <div className="employee-list">
                {ordersSender && ordersSender.map(order => (
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
                    </div>
                ))}
            </div>
        </>
        ) : (
            <p>Loading office details...</p>
        )}
                {(userRoles.includes('ADMIN') || userRoles.includes('COMPANY_MANAGER')) && (
                    <button onClick={() => navigate(-1)}>Back</button>
                )}
                <button onClick={handleLogout} className="logout-button">Logout</button>
            </div>

    );
};


export default ClientView;