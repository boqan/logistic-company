import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import axios from 'axios';
import {jwtDecode} from 'jwt-decode'


const CreateEmployee = () => {

    const navigate = useNavigate();
    const { officeId } = useParams(); // Extract officeId from URL
    const [userData, setUserData] = useState({
        
        name: '',
        salary: '',
        officeID: officeId,
        employeeType: ''
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
            console.log(userData);
            const response = await axios.post('http://localhost:8082/employee/save', userData, {
                headers: { Authorization: `Bearer ${token}` }
            });
            navigate(`/office-view/${officeId}`);
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
            <h1>Add Employee</h1>
            <Form onSubmit={handleSubmit}>
                <FormGroup>
                    <Label for="name">Name</Label>
                    <Input type="text" name="name" id="name" value={userData.name} onChange={handleChange} autoComplete="name" />
                </FormGroup>

                <FormGroup>
                    <Label for="salary">Salary</Label>
                    <Input type="text" name="salary" id="salary" value={userData.salary} onChange={handleChange} autoComplete="salary" />
                </FormGroup>

                <div className="form-group">
                    <label htmlFor="role">Role:</label>
                    <select
                        id="role"
                        name="employeeType"
                        className="custom-dropdown"
                        value={userData.employeeType}
                        onChange={handleChange} 
                        style={{ fontSize: '1.25rem', padding: '0.5rem 1rem' }}
                        required
                    >
                        <option value="COURIER">Courier</option>
                        <option value="DRIVER">Driver</option>
                        <option value="LOADER">Loader</option>
                        <option value="ACCOUNTANT">Accountant</option>
                        <option value="ADMINISTRATOR">Administrator</option>
                        
                    </select>
                </div>

                {errorMessage && <div className="alert alert-danger">{errorMessage}</div>}

                <FormGroup>
                    <Button color="primary" type="submit">Save</Button>{' '}
                    
                </FormGroup>
                <button onClick={() => navigate(`/office-view/${officeId}`)}>Cancel</button>
            </Form>
        </Container>
    </div>
    );
  }
  export default CreateEmployee;
