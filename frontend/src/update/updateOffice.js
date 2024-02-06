import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { jwtDecode } from 'jwt-decode';

const UpdateOffice = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [officeData, setOfficeData] = useState({ address: ''});
  const token = localStorage.getItem('token');
  
  useEffect(() => {
    const fetchCompanyData = async () => {
      try {
        const response = await axios.get(`http://localhost:8082/office/${id}`, {
          headers: { Authorization: `Bearer ${token}` }
        });
        setOfficeData({ address: response.data.address });
      } catch (error) {
        console.error('Error fetching company data:', error);
        // Handle error accordingly
      }
    };

    fetchCompanyData();
  }, [id, token]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setOfficeData(prevData => ({ ...prevData, [name]: value }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      await axios.put(`http://localhost:8082/office/${id}`, officeData, {
        headers: { Authorization: `Bearer ${token}` }
      });
      navigate(-1); // Redirect to profile or confirmation page
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
          <label htmlFor="name">Address:</label>
          <input
                type="text"
                id="address" // Changed from "name" to "address" to match the state property
                name="address" // Changed from "name" to "address" to match the state property
                value={officeData.address}
                onChange={handleInputChange}
                required
            />
        </div>
        <button type="submit">Submit Changes</button>
      </form>
    </div>
  );
};

export default UpdateOffice;
