import React, { useState , useEffect  } from 'react';
import axios from 'axios';
import {jwtDecode} from 'jwt-decode';
import { useNavigate ,useParams , Link   } from 'react-router-dom';
import '../styles/Signup.css'; // Ensure you have the corresponding CSS file

const CreateOffice = () => {
  const navigate = useNavigate();
  const { companyId } = useParams(); // Extract companyId from URL
  const [userData, setUserData] = useState({
    address: ''
  });
  const [userRoles, setUserRoles] = useState([]);
  const token = localStorage.getItem('token');
  const [userId, setUserId] = useState(null);

  const updateUserRoles = (decodedToken) => {
    const decodedRoles = decodedToken.roles ? decodedToken.roles.split(',') : [];
    setUserRoles(decodedRoles);
  };


  const [errorMessage, setErrorMessage] = useState('');
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
    // Proceed only if user is admin or company manager with matching company ID
    if (decodedRoles.includes('ADMIN') || (decodedRoles.includes('COMPANY_MANAGER') && decodedUserId.toString() === companyId)) {
    } else {
      navigate(-1);
    }
    //localStorage.setItem('companyId', companyId);
  }, [companyId, token, navigate, userId, userRoles]); // Include userId and userRoles in dependencies


  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setUserData({ ...userData, [name]: value });
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      //console.log(userData);
      //console.log(token);
      //console.log(companyId);
      const response = await axios.post('http://localhost:8082/office', userData, {
        headers: { Authorization: `Bearer ${token}` }
      });
      console.log(response);
      // Assuming successful response doesn't necessarily include an authentication token but a success indicator
      await axios.put(`http://localhost:8082/company/${companyId}/linkOffice/${response.data.id}`, userData , {
      headers: { Authorization: `Bearer ${token}` }
      });
      navigate(`/company-view/${companyId}`); // Adjust the route as necessary
    
    } catch (error) {
      console.error('Error creating office:', error);
      setErrorMessage(error.response?.data?.message || 'An error occurred while creating the office. Please try again.');
    }
  };

  return (
    <div className="signup-container">
      <form onSubmit={handleSubmit} className="signup-form">
        {errorMessage && <div className="error-message">{errorMessage}</div>}

        <div className="form-group">
          <label htmlFor="address">Address:</label>
          <input
            id="address"
            type="text"
            name="address"
            value={userData.address}
            onChange={handleInputChange}
            required
          />
        </div>

        <button type="submit" className="signup-button">Create Office</button>

        <div className="login-link">
          
        <Link to={`/company-view/${companyId}`}>Back to Offices</Link>
        </div>
      </form>
    </div>
  );
};

export default CreateOffice;
