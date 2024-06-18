import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import LoginForm from './components/LoginForm';
import UserDashboard from './components/UserDashboard';
import LibrarianDashboard from './components/LibrarianDashboard';
import AdminDashboard from './components/AdminDashboard';

const App = () => {
  const [userId, setUserID] = useState(null);
  const [userType, setUserType] = useState(null);

  useEffect(() => {
    console.log('UserID:', userId);
    console.log('UserType:', userType);
  }, [userId, userType]);

  if (!userId || !userType) {
    return <LoginForm setUserID={setUserID} setUserType={setUserType} />;
  }

  return (
    <Router>
      <Routes>
        <Route path="/" element={
          userType === 'USER' ? <UserDashboard userId={userId} setUserID={setUserID} setUserType={setUserType} /> :
          userType === 'LIBRARIAN' ? <LibrarianDashboard userId={userId} setUserID={setUserID} setUserType={setUserType} /> :
          <AdminDashboard userId={userId} setUserID={setUserID} setUserType={setUserType} />
        } />
      </Routes>
    </Router>
  );
};

export default App;
