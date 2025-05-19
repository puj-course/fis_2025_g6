import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomePage from './pages/HomePage';
import RegisterPage from './pages/RegisterPage';
import LoginPage from './pages/LoginPage';
import ProfilePage from './pages/ProfilePage';
import PetListPage from './pages/PetListPage';
import { AuthProvider } from './context/AuthContext';
import RoleRoute from './components/RoleRoute';
import ApplicationList from './pages/ApplicationList';
import RefugeListPage from './pages/RefugeList';

function App() {
    return (
        <AuthProvider>
            <Router>
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/registro" element={<RegisterPage />} />
                    <Route path="/iniciosesion" element={<LoginPage />} />
                    <Route path="/perfil" element={<ProfilePage />} />
                    <Route path="/lista-mascotas" element={<PetListPage />} />
                    <Route path="/lista-refugios" element={<RefugeListPage />} />
                    <Route
                        path="/solicitudes"
                        element={
                            <RoleRoute allowed={['ADOPTANT']}>
                                <ApplicationList />
                            </RoleRoute>
                        }
                    />
                </Routes>
            </Router>
        </AuthProvider>
    );
}

export default App;
