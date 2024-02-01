import React, { useState , useEffect  } from 'react';
import axios from 'axios';
import { useNavigate ,useParams  } from 'react-router-dom';
import '../styles/Signup.css'; // Ensure you have the corresponding CSS file

const CreateOffice = () => {
  const navigate = useNavigate();
  const { companyId } = useParams(); // Extract companyId from URL
  const [userData, setUserData] = useState({
    address: '',
    companyId: '',
    revenue: '0', // companyId will be set from URL
  });

  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    // Set companyId from URL when component mounts
    if (companyId) {
      setUserData(prevState => ({
        ...prevState,
        companyId: companyId,
      }));
    }
  }, [companyId]);

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setUserData({ ...userData, [name]: value });
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await axios.post('http://localhost:8082/office', userData);
      console.log(response.data);
      // Assuming successful response doesn't necessarily include an authentication token but a success indicator
      if (response.data.success) {
        navigate(`company-view/${companyId}`); // Adjust the route as necessary
      } else {
        setErrorMessage('Failed to create the office. Please try again.');
      }
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
          <button onClick={() => navigate(`company-view/${companyId}`)}>Back to Offices</button>
        </div>
      </form>
    </div>
  );
};

export default CreateOffice;
