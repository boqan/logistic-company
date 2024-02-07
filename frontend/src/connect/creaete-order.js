import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import axios from 'axios';
import {jwtDecode} from 'jwt-decode'


const CreateOrder = () => {

    const navigate = useNavigate();
    const { officeId } = useParams(); // Extract officeId from URL
    const [userData, setUserData] = useState({
        sender :'',
        receiver: '',
        weight: '',
        receiverAddress: '',
        deliveryType: 'TO_OFFICE'
    });
    const [userRoles, setUserRoles] = useState([]);
    const [errorMessage, setErrorMessage] = useState('');
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
        const decodedRoles = decodedToken.roles ? decodedToken.roles.split(',') : [];
        const decodedUserId = decodedToken.userId;
    
        // Prevent unnecessary state updates
        
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
                    if(decodedRoles.includes('COMPANY_MANAGER') && response.data.companyId===decodedUserId){
                    }
                    else if (decodedRoles.includes('ADMIN')){
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


    }, [token, navigate, userId, userRoles]);
   

  const handleChange = (event) => {
    const { name, value } = event.target;
    setUserData({ ...userData, [name]: value });
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await axios.post(`http://localhost:8082/orders/${officeId}`, userData, {
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
        setUserData({ ...userData, [name]: value });
      };
      
      return (
        <div>
          <Container>
            <h1>Create Order</h1>
            <Form onSubmit={handleSubmit}>
      
              <FormGroup>
                <Label for="sender">Sender</Label>
                <Input type="text" name="sender" id="sender" value={userData.sender} onChange={handleInputChange} autoComplete="off" />
              </FormGroup>
              <FormGroup>
                <Label for="receiver">Receiver</Label>
                <Input type="text" name="receiver" id="receiver" value={userData.receiver} onChange={handleInputChange} autoComplete="off" />
              </FormGroup>
              <FormGroup>
                <Label for="weight">Weight</Label>
                <Input type="text" name="weight" id="weight" value={userData.weight} onChange={handleInputChange} autoComplete="off" />
              </FormGroup>
              <FormGroup>
                <Label for="receiverAddress">Receiver Address</Label>
                <Input type="text" name="receiverAddress" id="receiverAddress" value={userData.receiverAddress} onChange={handleInputChange} autoComplete="off" />
              </FormGroup>

              <div className="form-group">
                    <label htmlFor="deliveryType">Delivery Type:</label>
                    <select
                        id="deliveryType"
                        name="deliveryType"
                        className="custom-dropdown"
                        value={userData.deliveryType}
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
  export default CreateOrder;
