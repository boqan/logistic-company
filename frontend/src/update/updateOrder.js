import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import axios from 'axios';
import {jwtDecode} from 'jwt-decode'

const OrderUpdate = () => {
    const { orderId } = useParams();
    const [errorMessage, setErrorMessage] = useState('');
    const navigate = useNavigate();
    const [orderData, setOrderData] = useState({   sender :'',
    receiver: '',
    weight: '',
    receiverAddress: '',
    deliveryType: '',
    status:''
});
    const token = localStorage.getItem('token');
 


useEffect(() => {
    const fetchCompanyData = async () => {
        try {
        const response = await axios.get(`http://localhost:8082/orders/${orderId}`, {
            headers: { Authorization: `Bearer ${token}` }
        });
        setOrderData({
            sender: response.data.sender,
            receiver: response.data.receiver,
            weight: response.data.weight,
            receiverAddress: response.data.receiverAddress,
            deliveryType:response.data.deliveryType,
            status:response.data.status

          });
        } catch (error) {
        console.error('Error fetching company data:', error);
        // Handle error accordingly
        }
    };

    fetchCompanyData();
    }, [orderId, token]);

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await axios.put(`http://localhost:8082/orders/${orderId}`, orderData, {
                headers: { Authorization: `Bearer ${token}` }
            });
            navigate(-1);
        } catch (error) {
            console.error('Error creating employee:', error);
            setErrorMessage(error.response?.data?.message || 'An error occurred while creating the employee. Please try again.');
        }
    };



    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setOrderData({ ...orderData, [name]: value });
      };
      
      return (
        <div>
          <Container>
            <h1>Update Order</h1>
            <Form onSubmit={handleSubmit}>
      
              <FormGroup>
                <Label for="sender">Sender</Label>
                <Input type="text" name="sender" id="sender" value={orderData.sender} onChange={handleInputChange} autoComplete="off" />
              </FormGroup>
              <FormGroup>
                <Label for="receiver">Receiver</Label>
                <Input type="text" name="receiver" id="receiver" value={orderData.receiver} onChange={handleInputChange} autoComplete="off" />
              </FormGroup>
              <FormGroup>
                <Label for="weight">Weight</Label>
                <Input type="text" name="weight" id="weight" value={orderData.weight} onChange={handleInputChange} autoComplete="off" />
              </FormGroup>
              <FormGroup>
                <Label for="receiverAddress">Receiver Address</Label>
                <Input type="text" name="receiverAddress" id="receiverAddress" value={orderData.receiverAddress} onChange={handleInputChange} autoComplete="off" />
              </FormGroup>
              <div className="form-group">
                    <label htmlFor="status">Delivery Status:</label>
                    <select
                        id="status"
                        name="status"
                        className="custom-dropdown"
                        value={orderData.status}
                        onChange={handleInputChange} 
                        style={{ fontSize: '1.25rem', padding: '0.5rem 1rem' }}
                        required
                    >
                        <option value="PENDING">Pending</option>
                        <option value="DELIVERED">Delivered</option>
                        <option value="CANCELLED">Cancled</option>
                        
                        
                    </select>
                </div>

              <div className="form-group">
                    <label htmlFor="deliveryType">Delivery Type:</label>
                    <select
                        id="status"
                        name="deliveryType"
                        className="custom-dropdown"
                        value={orderData.deliveryType}
                        onChange={handleInputChange} 
                        style={{ fontSize: '1.25rem', padding: '0.5rem 1rem' }}
                        required
                    >
                        <option value="TO_OFFICE">To office</option>
                        <option value="TO_HOME_ADDRESS">To home address</option>
                        
                        
                    </select>
                </div>
      
              {errorMessage && <div className="alert alert-danger">{errorMessage}</div>}
      
              <FormGroup>
                <Button color="primary" type="submit">Save</Button>{' '}
              </FormGroup>
              <button onClick={() => navigate(-1)}>Cancel</button>
            </Form>
          </Container>
        </div>
      );

}
export default OrderUpdate;
