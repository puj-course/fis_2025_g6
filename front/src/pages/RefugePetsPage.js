import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Footer from '../components/Footer';
import Header from '../components/Header';
import { Modal, Button, Form } from 'react-bootstrap';
import { translatePetStatus } from '../util/Util';

export default function RefugePetsPage() {
    const [requests, setRequests] = useState([]);
    const [showRequestsModal, setShowRequestsModal] = useState(false);
    const [pets, setPets] = useState([]);
    const [showModal, setShowModal] = useState(false);
    const [newPet, setNewPet] = useState({
        name: '',
        species: '',
        age: '',
        sex: '',
        description: ''
    });

    useEffect(() => {
        const fetchPets = async () => {
            try {
                const response = await axios.get('http://localhost:8080/mascotas/me', {
                    headers: {
                        Authorization: `Bearer ${localStorage.getItem('token')}`,
                    },
                });
                setPets(response.data);
            } catch (error) {
                console.error('Error al obtener mascotas:', error);
            }
        };

        fetchPets();
    }, []);

    const handleAddPet = (e) => {
        e.preventDefault();
        axios.post('http://localhost:8080/mascotas', newPet, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`,
            },
        })
            .then((res) => {
                setPets((prevPets) => [...prevPets, res.data]);
                setShowModal(false);
                setNewPet({ name: '', species: '', age: '', sex: '', description: '' });
            })
            .catch((err) => console.error(err));
    };

    const handleViewRequests = (petId) => {
        axios.get(`http://localhost:8080/mascotas/${petId}/solicitudes`, {
            headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
        }).then((res) => {
            setRequests(res.data);
            setShowRequestsModal(true);
        }).catch(console.error);
    };

    const updateRequestStatus = (requestId, newStatus) => {
        axios.put(`http://localhost:8080/solicitudes/${requestId}`, {
            status: newStatus
        }, {
            headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
        }).then(() => {
            setRequests(prev => prev.map(r => r.id === requestId ? { ...r, status: newStatus } : r));
        }).catch(console.error);
    };
    return (
        <>
            <Header />
            <div className="container mt-4">
                <h1 className="mb-4">Mis mascotas</h1>
                {pets.length === 0 ? (
                    <p>No tienes mascotas registradas.</p>
                ) : (
                    <div className="row">
                        {pets.map((pet) => (
                            <div className="col-md-4 mb-4" key={pet.id}>
                                <div className="card h-100">
                                    <div className="card-body">
                                        <h5 className="card-title">{pet.name}</h5>
                                        <p className="card-text"><strong>Especie:</strong> {pet.species}</p>
                                        <p className="card-text"><strong>Edad:</strong> {pet.age}</p>
                                        <p className="card-text"><strong>Sexo:</strong> {pet.sex}</p>
                                        <p className="card-text"><strong>Descripción:</strong> {pet.description}</p>
                                        <p className="card-text"><strong>Estado:</strong> {translatePetStatus(pet.status)}</p>
                                        <button className="btn btn-info mt-2" onClick={() => handleViewRequests(pet.id)}>Ver solicitudes</button>
                                        <br />
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>
                )}
                <button className="btn btn-primary" onClick={() => setShowModal(true)}>Nueva mascota</button>
            </div>

            <Modal show={showModal} onHide={() => setShowModal(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>Nueva mascota</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={handleAddPet}>
                        <Form.Group className="mb-3">
                            <Form.Label>Nombre</Form.Label>
                            <Form.Control
                                type="text"
                                value={newPet.name}
                                onChange={(e) => setNewPet({ ...newPet, name: e.target.value })}
                                required
                            />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Especie</Form.Label>
                            <Form.Control
                                type="text"
                                value={newPet.species}
                                onChange={(e) => setNewPet({ ...newPet, species: e.target.value })}
                                required
                            />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Edad</Form.Label>
                            <Form.Control
                                type="number"
                                value={newPet.age}
                                onChange={(e) => setNewPet({ ...newPet, age: e.target.value })}
                                required
                            />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Sexo</Form.Label>
                            <Form.Select
                                value={newPet.sex}
                                onChange={(e) => setNewPet({ ...newPet, sex: e.target.value })}
                                required
                            >
                                <option value="">Seleccionar</option>
                                <option value="Macho">Macho</option>
                                <option value="Hembra">Hembra</option>
                            </Form.Select>
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>Descripción</Form.Label>
                            <Form.Control
                                as="textarea"
                                value={newPet.description}
                                onChange={(e) => setNewPet({ ...newPet, description: e.target.value })}
                                required
                            />
                        </Form.Group>
                        <Button variant="primary" type="submit">
                            Aceptar
                        </Button>
                    </Form>
                </Modal.Body>
            </Modal>

            <Modal show={showRequestsModal} onHide={() => setShowRequestsModal(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>Solicitudes</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {requests.length === 0 ? (
                        <p>No hay solicitudes.</p>
                    ) : (
                        requests.map((req) => (
                            <div key={req.id} className="mb-3 border rounded p-2">
                                <p><strong>Fecha: </strong>{req.date}</p>
                                <p><strong>Adoptante: </strong>{req.adoptant.adoptantName} ({req.adoptant.email})</p>
                                <div className="d-flex gap-2">
                                    <button className="btn btn-success" onClick={() => updateRequestStatus(req.id, 'APPROVED')}>Aprobar</button>
                                    <button className="btn btn-danger" onClick={() => updateRequestStatus(req.id, 'REJECTED')}>Rechazar</button>
                                </div>
                            </div>
                        ))
                    )}
                </Modal.Body>
            </Modal>

            <Footer />
        </>
    );
}
