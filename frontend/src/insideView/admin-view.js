import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { jwtDecode } from 'jwt-decode';
import '../styles/AdminView.css'; // Make sure your CSS file is correctly imported

const AdminView = () => {
  const [error, setError] = useState('');
  const navigate = useNavigate();
  const token = localStorage.getItem('token');
  const [companies, setCompanies] = useState([]);

  

  useEffect(() => {
    if (!token ) {
      navigate('/login');
      return;
    }
    console.log(token);
    try {
      const decodedToken = jwtDecode(token);
      const userRoles = decodedToken.roles ; // Adjust based on your token structure

      // Check if user has the 'ADMIN' role
      if (!userRoles.includes('ADMIN')) {
        navigate('/login'); // or to another appropriate route
        return;
      }
    } catch (error) {
      console.error('Error decoding token:', error);
      navigate('/login');
    }

    const fetchCompanies = async () => {
      try {
        const response = await axios.get('http://localhost:8082/company', {
          headers: { Authorization: `Bearer ${token}` }
        });
        setCompanies(response.data);
      } catch (error) {
        console.error('Error fetching companies:', error);
      }
    };

    fetchCompanies();
  }, [token, navigate]);


  const handleLogout = () => {
    localStorage.removeItem('token');
    navigate('/login');
  };

  const deleteCompany = async (companyId) => {
    try {
      await axios.delete(`http://localhost:8082/company/${companyId}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      // Remove the company from the list without refreshing the page
      setCompanies(companies.filter(company => company.id !== companyId));
    } catch (error) {
      console.error('Error deleting company:', error);
      // Optionally handle the error, e.g., show a notification
      setError("There is connected information to this company. You can't delete it!");
    }
  };

  return (
    <div className="profile-container">
     <h1>Companies</h1>
      {error && <div className="error-message">{error}</div>} {/* Display error message */}
      <div className="companies-list">
        {companies.map(company => (
         <div className="company-box" key={company.id}>
            <h2>{company.name}</h2>
            <div className="buttons-container">
              <button onClick={() => navigate(`/company-view/${company.id}`)} className="view-button"> View </button>
                <button onClick={() => navigate(`/update-company/${company.id}`)} className="update-button"> Update </button>
                <button onClick={() => deleteCompany(company.id)} className="delete-button">Delete</button>
            </div>
          </div>
        ))}
      </div>
      
      <button onClick={handleLogout} className="logout-button">Logout</button>
    </div>
  );
};

export default AdminView;
