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
import RefugePetsPage from './pages/RefugePetsPage';
import DonationsPage from './pages/DonationsPage';

function App() {
    return (
        <AuthProvider>
            <Router>
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/registro" element={<RegisterPage />} />
                    <Route path="/iniciosesion" element={<LoginPage />} />
                    <Route path="/perfil" element={<ProfilePage />} />

                    {/* Rutas para ADOPTANT */}
                    <Route
                        path="/lista-mascotas"
                        element={
                            <RoleRoute allowed={['ADOPTANT']}>
                                <PetListPage />
                            </RoleRoute>
                        }
                    />
                    <Route
                        path="/lista-refugios"
                        element={
                            <RoleRoute allowed={['ADOPTANT']}>
                                <RefugeListPage />
                            </RoleRoute>
                        }
                    />
                    <Route
                        path="/solicitudes"
                        element={
                            <RoleRoute allowed={['ADOPTANT']}>
                                <ApplicationList />
                            </RoleRoute>
                        }
                    />

                    {/* Rutas para REFUGE */}
                    <Route
                        path="/donaciones"
                        element={
                            <RoleRoute allowed={['REFUGE']}>
                                <DonationsPage />
                            </RoleRoute>
                        }
                    />
                    <Route
                        path="/mascotas"
                        element={
                            <RoleRoute allowed={['REFUGE']}>
                                <RefugePetsPage />
                            </RoleRoute>
                        }
                    />
                </Routes>
            </Router>
        </AuthProvider>
    );
}

export default App;
