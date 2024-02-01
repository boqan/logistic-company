import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate ,Link } from 'react-router-dom';
import '../styles/Signup.css'; // Ensure you have the corresponding CSS file

const Signup = () => {
  const navigate = useNavigate();
  const [userData, setUserData] = useState({
    address: '',
    companyId:'',
    
  });

  const [errorMessage, setErrorMessage] = useState('');

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setUserData({ ...userData, [name]: value });
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      // Make sure to point to the registration endpoint
      const response = await axios.post('http://localhost:8082/office', userData);
      console.log(response.data);
      if (response.data.authenticationToken) {
        // Store the token and redirect to profile or a welcome page
        localStorage.setItem('token', response.data.authenticationToken);
        navigate('/'); // Adjust as needed based on your routes
      } else {
        setErrorMessage('Registration failed. Please try again.');
      }
    } catch (error) {
      console.error('Registration error:', error);
      setErrorMessage(error.response?.data?.message || 'An error occurred during registration. Please try again.');
    }
  };

  return (
    <div className="signup-container">
      <form onSubmit={handleSubmit} className="signup-form">
        {errorMessage && <div className="error-message">{errorMessage}</div>}

        <div className="form-group">
          <label htmlFor="username">Username:</label>
          <input
            id="username"
            type="text"
            name="username"
            value={userData.username}
            onChange={handleInputChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="email">Email:</label>
          <input
            id="email"
            type="email"
            name="email"
            value={userData.email}
            onChange={handleInputChange}
            required
          />
        </div>


        <div className="form-group">
          <label htmlFor="password">Password:</label>
          <input
            id="password"
            type="password"
            name="password"
            value={userData.password}
            onChange={handleInputChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="role">Role:</label>
          <select
            id="role"
            name="role"
            value={userData.role}
            onChange={handleInputChange}
            required
          >
            <option value="CLIENT">Client</option>
            <option value="EMPLOYEE">Employee</option>
          </select>
      </div>
              

        <button type="submit" className="signup-button">Sign Up</button>

        <div className="login-link">
          <p>
            Already have an account? <Link to="/login">Login</Link>
          </p>
        </div>
      </form>
    </div>
  );
};

export default Signup;
