import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios'; // Make sure axios is imported

const ClientUpdate = () => {
    const { clientId } = useParams();
    const navigate = useNavigate();
    const [clientData, setClientData] = useState({  name: '',
    officeID:''
});
    const token = localStorage.getItem('token');
 


useEffect(() => {
    const fetchCompanyData = async () => {
        try {
        const response = await axios.get(`http://localhost:8082/client/${clientId}`, {
            headers: { Authorization: `Bearer ${token}` }
        });
        console.log(response.data);
        setClientData({
            name: response.data.name,
            officeID: response.data.officeID
          });
        } catch (error) {
        console.error('Error fetching company data:', error);
        // Handle error accordingly
        }
    };

    fetchCompanyData();
    }, [clientId, token]);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setClientData(prevData => ({ ...prevData, [name]: value }));
    };
    
  
  
  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      await axios.put(`http://localhost:8082/client/${clientId}`, clientData,  {
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
      <h1>Update Client</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="name">Name:</label>
          <input
                type="text"
                id="name" // Changed from "name" to "address" to match the state property
                name="name" // Changed from "name" to "address" to match the state property
                value={clientData.name}
                onChange={handleInputChange}
                required
            />
        </div>
    
        <button type="submit">Submit Changes</button>
      </form>
    </div>
  );

}
export default ClientUpdate;
