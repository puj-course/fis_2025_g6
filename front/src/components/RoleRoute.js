import React from 'react';
import { Navigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const RoleRoute = ({ allowed, children }) => {
    const { userType } = useAuth();

    if (!userType) {
        // Usuario no autenticado
        return <Navigate to="/iniciosesion" />;
    }

    if (!allowed.includes(userType)) {
        // Usuario autenticado pero sin permisos
        return <Navigate to="/" />;
    }

    return children;
};

export default RoleRoute;
