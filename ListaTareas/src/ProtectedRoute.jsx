import React from 'react';
import { Navigate, Outlet } from 'react-router-dom';

const ProtectedRoute = () => {
    const isAuthenticated = () => {
        const token = localStorage.getItem('token');
        return !!token;
    };

    const isAuth = isAuthenticated();
    return isAuth ? <Outlet /> : <Navigate to="/" />;
};

export default ProtectedRoute;