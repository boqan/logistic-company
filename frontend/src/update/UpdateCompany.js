import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { jwtDecode } from 'jwt-decode';

const UpdateCompany = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [companyData, setCompanyData] = useState({ adress: '', country: '' });
  const token = localStorage.getItem('token');
  
  useEffect(() => {
    const fetchCompanyData = async () => {
      try {
        const response = await axios.get(`http://localhost:8082/company/${id}`, {
          headers: { Authorization: `Bearer ${token}` }
        });
        setCompanyData({ adress: response.data.name, country: response.data.country });
      } catch (error) {
        console.error('Error fetching company data:', error);
        // Handle error accordingly
      }
    };

    fetchCompanyData();
  }, [id, token]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setCompanyData(prevData => ({ ...prevData, [name]: value }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      await axios.put(`http://localhost:8082/company/${id}`, companyData, {
        headers: { Authorization: `Bearer ${token}` }
      });
      navigate('/admin-view'); // Redirect to profile or confirmation page
    } catch (error) {
      console.error('Error updating company:', error);
      // Handle error accordingly
    }
  };

  return (
    <div>
      <h1>Update Company</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="name">Name:</label>
          <input
            type="text"
            id="name"
            name="name"
            value={companyData.name}
            onChange={handleInputChange}
            required
          />
        </div>
        <div>
          <label htmlFor="country">Country:</label>
          <input
            type="text"
            id="country"
            name="country"
            value={companyData.country}
            onChange={handleInputChange}
            required
          />
        </div>
        <button type="submit">Submit Changes</button>
      </form>
    </div>
  );
};

export default UpdateCompany;
