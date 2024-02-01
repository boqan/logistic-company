import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate, Link } from 'react-router-dom';
import '../styles/Signup.css'; // Ensure you have the corresponding CSS file

const CreateCompany = () => {
  const navigate = useNavigate();
  
  // Initialize user data state
  const [userData, setUserData] = useState({
    username: '',
    password: '',
    email: '',
    companyName: '', // Assuming this refers to the company name
    country: '',
  });

  const [errorMessage, setErrorMessage] = useState('');

  // Handle input change and update userData state
  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setUserData(prevState => ({ ...prevState, [name]: value }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
        console.log(userData);
      // Attempt to create the company
      const companyResponse = await axios.post('http://localhost:8082/api/v1/auth/register_company', userData);
      
      // Check if the company creation was successful
      if (!companyResponse.data || !companyResponse.data.id) {
        setErrorMessage('Company creation failed. Please try again.');
        return;
      }

      // Navigate to a different route or show a success message upon successful signup
      // navigate('/success-route'); // Uncomment and modify as needed

    } catch (error) {
      console.error('Company creation error:', error);
      setErrorMessage(error.response?.data?.message || 'An error occurred during company creation. Please try again.');
    }
  };

  return (
    <div className="signup-container">
      <form onSubmit={handleSubmit} className="signup-form">
        {errorMessage && <div className="error-message">{errorMessage}</div>}

        <div className="form-group">
          <label htmlFor="companyName">Company name:</label>
          <input
            id="companyName"
            type="text"
            name="companyName"
            value={userData.companyName}
            onChange={handleInputChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="country">Country:</label>
          <input
            id="country"
            type="text"
            name="country"
            value={userData.country}
            onChange={handleInputChange}
            required
          />
        </div>

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


        <button type="submit" className="signup-button">Sign Up</button>

        <div className="login-link">
          <p>Already have an account? <Link to="/login">Login</Link></p>
        </div>
      </form>
    </div>
  );
};

export default CreateCompany;
