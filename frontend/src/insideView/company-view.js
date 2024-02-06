import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import {jwtDecode} from 'jwt-decode';
import '../styles/CompanyDetails.css'; // Make sure the path is correct

const CompanyDetails = () => {
  const { companyId } = useParams();
  const navigate = useNavigate();
  const [company, setCompany] = useState(null);
  const [userRoles, setUserRoles] = useState([]);
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
    // Proceed only if user is admin or company manager with matching company ID
    if (decodedRoles.includes('ADMIN') || (decodedRoles.includes('COMPANY_MANAGER') && decodedUserId.toString() === companyId)) {
      const fetchCompanyDetails = async () => {
        try {
          const response = await axios.get(`http://localhost:8082/company/${companyId}`, {
            headers: { Authorization: `Bearer ${token}` }
          });
          setCompany(response.data);
          
        } catch (error) {
          console.error('Error fetching company details:', error);
          navigate(-1);
        }
      };
     

      fetchCompanyDetails();
    } else {
      navigate(-1);
    }
    //localStorage.setItem('companyId', companyId);
  }, [companyId, token, navigate, userId, userRoles]); // Include userId and userRoles in dependencies

  const deleteOffice = async (officeId) => {
    try {
      await axios.delete(`http://localhost:8082/office/${officeId}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setCompany(prevCompany => ({
        ...prevCompany,
        offices: prevCompany.offices.filter(office => office.id !== officeId)
      }));
    } catch (error) {
      console.error('Error deleting office:', error);
    }
  };

  const handleLogout = () => {
    localStorage.removeItem('token');
    navigate('/login');
  };


  if (!company) {
    return <div>Loading company details...</div>;
  }

  return (
    <div>
      <h1>Company: {company.name}</h1>
      <h1>Officess</h1>
      <div className="offices-list">
        {company.offices && company.offices.map(office => (
          <div className="office-box" key={office.id}>
            <div className="office-header">
            <div className="office-details">
                  <h2>Address: {office.address}</h2>
                  <h2>Revenue: {office.revenue}</h2>
                  
              </div>    
              <div>
                <button onClick={() => navigate(`/office-view/${office.id}`)} className="view-button"> View </button>
                <button onClick={() => navigate(`/update-office/${office.id}`)} className="update-button"> Update </button>
                <button onClick={() => deleteOffice(office.id)} className="office-button delete-button">Delete</button>
              </div>
            </div>
          </div>
        ))}
      </div>
      <button onClick={() =>navigate(`/create-office/${companyId}`)}>Create Office</button>
      {userRoles.includes('ADMIN') && (
        <button onClick={() =>navigate(-1)}>Back to Companies</button>
      )}
       <button onClick={handleLogout} className="logout-button">Logout</button>
    </div>
  );
};

export default CompanyDetails;


