import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Header from '../components/Header';
import Footer from '../components/Footer';

const ApplicationListPage = () => {
    const [applications, setApplications] = useState([]);

    const fetchMyApplications = async () => {
        try {
            const token = localStorage.getItem('token');
            const res = await axios.get('http://localhost:8080/solicitudes/me', {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            setApplications(res.data);
        } catch (error) {
            console.error('Error al obtener solicitudes del usuario', error);
        }
    };

    const cancelApplication = async (id) => {
        try {
            const token = localStorage.getItem('token');
            await axios.delete(`http://localhost:8080/solicitudes/${id}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            // Eliminar de la lista local
            setApplications(applications.filter(app => app.id !== id));
        } catch (error) {
            console.error('Error al cancelar la solicitud', error);
        }
    };

    useEffect(() => {
        fetchMyApplications();
    }, []);

    const translateStatus = (status) => {
        switch (status) {
            case 'PENDING':
                return 'Pendiente';
            case 'ACCEPTED':
                return 'Aceptada';
            case 'REJECTED':
                return 'Rechazada';
            default:
                return status;
        }
    };

    return (
        <>
            <Header />
            <div className="container mt-4">
                <h1 className="mb-4">Mis solicitudes de adopción</h1>
                {applications.length === 0 ? (
                    <p>No tienes solicitudes registradas.</p>
                ) : (
                    <div className="row">
                        {applications.map((app) => (
                            <div key={app.id} className="col-md-6 mb-4">
                                <div className="card h-100">
                                    <div className="card-body">
                                        <h5 className="card-title">Solicitud #{app.id}</h5>
                                        <p><strong>Fecha:</strong> {new Date(app.date).toLocaleDateString()}</p>
                                        <p><strong>Estado:</strong> {translateStatus(app.status)}</p>
                                        {app.pet && (
                                            <p><strong>Mascota:</strong> {app.pet.name} ({app.pet.species})</p>
                                        )}
                                        {app.status === 'PENDING' && (
                                            <button
                                                className="btn btn-danger mt-2"
                                                onClick={() => cancelApplication(app.id)}
                                            >
                                                Cancelar
                                            </button>
                                        )}
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>
                )}
            </div>
            <Footer />
        </>
    );
};

export default ApplicationListPage;
