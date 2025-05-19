import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Modal } from 'bootstrap';
import Header from '../components/Header';
import Footer from '../components/Footer';
import PetCard from "../components/PetCard";

const PetListPage = () => {
    const [pets, setPets] = useState([]);
     // eslint-disable-next-line
    const [_refuges, setRefuges] = useState([]);
    const [filters, setFilters] = useState({
        species: '',
        age: '',
        sex: '',
        status: '',
        refugeId: ''
    });
    const [selectedRefuge, setSelectedRefuge] = useState(null);
    const [applicationSummary, setApplicationSummary] = useState(null);
    const [userApplications, setUserApplications] = useState([]);

    const fetchPets = async () => {
        try {
            const token = localStorage.getItem('token');
            const res = await axios.get('http://localhost:8080/mascotas', {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            setPets(res.data);
        } catch (error) {
            console.error('Error al obtener mascotas', error);
        }
    };

    const fetchRefuges = async () => {
        try {
            const token = localStorage.getItem('token');
            const res = await axios.get('http://localhost:8080/refugios', {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            setRefuges(res.data);
        } catch (error) {
            console.error('Error al obtener refugios', error);
        }
    };

    const fetchUserApplications = async () => {
        try {
            const token = localStorage.getItem('token');
            const res = await axios.get('http://localhost:8080/solicitudes/me', {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            setUserApplications(res.data);
        } catch (error) {
            console.error('Error al obtener solicitudes del usuario', error);
        }
    };

    const handleFilter = async () => {
        try {
            const token = localStorage.getItem('token');
            const queryParams = new URLSearchParams();

            Object.entries(filters).forEach(([key, value]) => {
                if (value) queryParams.append(key, value);
            });

            const res = await axios.get(`http://localhost:8080/mascotas/filtro?${queryParams.toString()}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });

            setPets(res.data);
        } catch (error) {
            console.error('Error al filtrar mascotas', error);
        }
    };

    const notifyEmail = async (subject, message) => {
        try {
            const token = localStorage.getItem('token');

            const queryParams = new URLSearchParams();

            queryParams.append('subject', subject);
            queryParams.append('message', message);

            const res = await axios.post(
                `http://localhost:8080/email/me?${queryParams.toString()}`, {}, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );

            console.log(res.data);
        } catch (error) {
            console.error('Error al enviar notificación', error);
        }
    };

    const notifySMS = async (number, message) => {
        try {
            const token = localStorage.getItem('token');

            const queryParams = new URLSearchParams();

            queryParams.append('number', number);
            queryParams.append('message', message);

            const res = await axios.post(
                `http://localhost:8080/sms/me?${queryParams.toString()}`, {}, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );

            console.log(res.data);
        } catch (error) {
            console.error('Error al enviar notificación', error);
        }
    };

    useEffect(() => {
        fetchPets();
        fetchRefuges();
        fetchUserApplications();
    }, []);

    const hasActiveApplication = (petId) => {
        return userApplications.some(app =>
            app.pet.id === petId && (app.status === 'PENDING' || app.status === 'ACCEPTED')
        );
    };
    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFilters({ ...filters, [name]: value });
    };

    const showRefugeInfo = (refuge) => {
        setSelectedRefuge(refuge);
        const modal = new Modal(document.getElementById('refugeModal'));
        modal.show();
    };

    const translateStatus = status => {
        switch (status) {
            case 'AVAILABLE':
                return 'Disponible';
            case 'IN_PROCESS':
                return 'En proceso';
            case 'ADOPTED':
                return 'Adoptado';
            default:
                return '';
        }
    }

    const handleApply = async (petId) => {
        try {
            const token = localStorage.getItem('token');
            const res = await axios.post(
                'http://localhost:8080/solicitudes',
                { petId },
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );

            setApplicationSummary(res.data);
            const modal = new Modal(document.getElementById('applicationModal'));
            modal.show();
        } catch (error) {
            console.error('Error al crear solicitud', error);
            alert('No se pudo crear la solicitud. Verifica si ya has aplicado.');
        }
    };

    const sendEmailConfirmation = async () => {
        if (!applicationSummary) return;
        const { id, date, status, pet, adoptant } = applicationSummary;

        const subject = `Solicitud de adopción #${id}`;
        const message = `
Resumen de solicitud
ID de solicitud: ${id}
Fecha: ${date}
Estado: ${translateStatus(status)}

--- Mascota ---
Nombre: ${pet.name}
Especie: ${pet.species}
Edad: ${pet.age}
Sexo: ${pet.sex}
Descripción: ${pet.description}
Nombre del refugio: ${pet.refuge.refugeName}

--- Adoptante ---
Nombre: ${adoptant.adoptantName}
Correo electrónico: ${adoptant.email}
Teléfono: ${adoptant.phoneNumber}
Dirección: ${adoptant.address}
        `.trim();

        await notifyEmail(subject, message);
    };

    const sendSmsConfirmation = async () => {
        if (!applicationSummary) return;
        const { id, pet, adoptant } = applicationSummary;

        const number = adoptant.phoneNumber;
        const message = `Solicitud de adopción #${id} para ${pet.name}`;

        await notifySMS(number, message);
    };

    return (
        <>
            <Header />
            <div className="container mt-4">
                <h1 className="mb-4">Lista de mascotas</h1>

                <div className="card p-3 mb-4">
                    <div className="row g-3">
                        <div className="col-md-2">
                            <label className="form-label">Especie</label>
                            <input type="text" name="species" value={filters.species} onChange={handleInputChange} className="form-control" />
                        </div>
                        <div className="col-md-2">
                            <label className="form-label">Edad</label>
                            <input type="number" name="age" min="0" value={filters.age} onChange={handleInputChange} className="form-control" />
                        </div>
                        <div className="col-md-2">
                            <label className="form-label">Sexo</label>
                            <select name="sex" value={filters.sex} onChange={handleInputChange} className="form-select">
                                <option value="">Todos</option>
                                <option value="Macho">Macho</option>
                                <option value="Hembra">Hembra</option>
                            </select>
                        </div>
                        <div className="col-md-2">
                            <label className="form-label">Estado</label>
                            <select name="status" value={filters.status} onChange={handleInputChange} className="form-select">
                                <option value="">Todos</option>
                                <option value="AVAILABLE">Disponible</option>
                                <option value="IN_PROCESS">En proceso</option>
                                <option value="ADOPTED">Adoptado</option>
                            </select>
                        </div>
                        <div className="col-md-2 d-flex align-items-end">
                            <button onClick={handleFilter} className="btn btn-primary w-100">Filtrar</button>
                        </div>
                    </div>
                </div>

                <div className="row">
                    {pets.map((pet) => (
                        <PetCard
                        key={pet.id}
                        pet={pet}
                        hasActiveApplication={hasActiveApplication}
                        onApply={handleApply}
                        onShowRefuge={showRefugeInfo}
                        />
                    ))}
                    </div>
            </div>

            {/* Modal */}
            <div className="modal fade" id="refugeModal" tabIndex="-1" aria-labelledby="refugeModalLabel" aria-hidden="true">
                <div className="modal-dialog">
                    <div className="modal-content">
                        {selectedRefuge && (
                            <>
                                <div className="modal-header">
                                    <h5 className="modal-title" id="refugeModalLabel">Información del refugio</h5>
                                    <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
                                </div>
                                <div className="modal-body">
                                    <p><strong>Nombre:</strong> {selectedRefuge.refugeName}</p>
                                    <p><strong>Correo electrónico:</strong> {selectedRefuge.email}</p>
                                    <p><strong>Teléfono:</strong> {selectedRefuge.phoneNumber}</p>
                                    <p><strong>Dirección:</strong> {selectedRefuge.address}</p>
                                    <p><strong>Descripción:</strong> {selectedRefuge.description}</p>
                                </div>
                                <div className="modal-footer">
                                    <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                                </div>
                            </>
                        )}
                    </div>
                </div>
            </div>
            <div className="modal fade" id="applicationModal" tabIndex="-1" aria-labelledby="applicationModalLabel" aria-hidden="true">
                <div className="modal-dialog modal-lg">
                    <div className="modal-content">
                        {applicationSummary && (
                            <>
                                <div className="modal-header">
                                    <h5 className="modal-title" id="applicationModalLabel">Resumen de solicitud</h5>
                                    <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
                                </div>
                                <div className="modal-body">
                                    <p><strong>ID de solicitud:</strong> {applicationSummary.id}</p>
                                    <p><strong>Fecha:</strong> {applicationSummary.date}</p>
                                    <p><strong>Estado:</strong> {translateStatus(applicationSummary.status)}</p>
                                    <hr />
                                    <h6>Mascota:</h6>
                                    <p><strong>Nombre:</strong> {applicationSummary.pet.name}</p>
                                    <p><strong>Especie:</strong> {applicationSummary.pet.species}</p>
                                    <p><strong>Edad:</strong> {applicationSummary.pet.age}</p>
                                    <p><strong>Sexo:</strong> {applicationSummary.pet.sex}</p>
                                    <p><strong>Descripción:</strong> {applicationSummary.pet.description}</p>
                                    <p><strong>Nombre del refugio:</strong> {applicationSummary.pet.refuge.refugeName}</p>
                                    <hr />
                                    <h6>Adoptante:</h6>
                                    <p><strong>Nombre:</strong> {applicationSummary.adoptant.adoptantName}</p>
                                    <p><strong>Correo electrónico:</strong> {applicationSummary.adoptant.email}</p>
                                    <p><strong>Teléfono:</strong> {applicationSummary.adoptant.phoneNumber}</p>
                                    <p><strong>Dirección:</strong> {applicationSummary.adoptant.address}</p>
                                </div>
                                <div className="modal-footer">
                                    <button type="button" className="btn btn-primary" onClick={sendEmailConfirmation}>
                                        Enviar confirmación por correo
                                    </button>
                                    <button type="button" className="btn btn-primary" onClick={sendSmsConfirmation}>
                                        Enviar confirmación por SMS
                                    </button>

                                    <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                                </div>
                            </>
                        )}
                    </div>
                </div>
            </div>

            <Footer />
        </>
    );
};

export default PetListPage;
