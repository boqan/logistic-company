import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate, Link } from 'react-router-dom';
import '../styles/Login.css'; // Make sure you have your CSS file for styling
import { jwtDecode } from 'jwt-decode';

const Login = () => {
  const navigate = useNavigate();
  const [credentials, setCredentials] = useState({ username: '', password: '' });
  const [errorMessage, setErrorMessage] = useState('');
  

  const handleChange = (e) => {
    setCredentials({ ...credentials, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await axios.post('http://localhost:8082/api/v1/auth/authenticate', credentials);
      if (response.data.authenticationToken) {
        localStorage.setItem('token', response.data.authenticationToken);
        
        // Decode token and extract user role
        const decodedToken = jwtDecode(response.data.authenticationToken);
        const userRole = decodedToken.role || decodedToken.roles; // Adjust based on your token's structure
        const decodedUserId = decodedToken.userId;
        // Redirect user based on role
        if (userRole === 'ADMIN') {
          navigate('/admin-view');
        } else if (userRole === 'CLIENT') {
          navigate('/client-view');
        } else if (userRole === 'EMPLOYEE') {
          navigate('/employee-view');
        } else if (userRole === 'COMPANY_MANAGER') {
          navigate(`/company-view/${decodedUserId}`);
        } else {
          setErrorMessage('Role not recognized. Please contact support.');
        }
      } else {
        setErrorMessage('Login failed. Please try again.');
      }
    } catch (error) {
      console.error('Login error:', error);
      setErrorMessage(error.response?.data?.message || 'An error occurred. Please try again.');
    }
  };

  return (
    <div className="login-container">
      <form onSubmit={handleSubmit} className="login-form">
        {errorMessage && <div className="error-message">{errorMessage}</div>}

        <div className="form-group">
          <label htmlFor="username">Username:</label>
          <input
            id="username"
            type="text"
            name="username"
            value={credentials.username}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="password">Password:</label>
          <input
  id="password"
  type="password"
  name="password"
  autoComplete="current-password"
  value={credentials.password}
  onChange={handleChange}
  required
/>
        </div>

        <button type="submit" className="login-button">Login</button>

        <div className="login-link">
          <p>
            Don't have an account? <Link to="/create-company">Create Company</Link>
          </p>
        </div>
      </form>
    </div>
  );
};

export default Login;
