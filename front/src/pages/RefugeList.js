import React from 'react';
import { useEffect, useState } from "react";
import axios from "axios";
import { Modal, Button, Form } from 'react-bootstrap';
import Header from "../components/Header";
import Footer from "../components/Footer";

const RefugeListPage = () => {
    const [refuges, setRefuges] = useState([]);
    const [selectedRefuge, setSelectedRefuge] = useState(null);
    const [showModal, setShowModal] = useState(false);
    const [showDonationModal, setShowDonationModal] = useState(false);
    const [donationAmount, setDonationAmount] = useState("");
    const [donationRefuge, setDonationRefuge] = useState("");

    const fetchRefuges = async () => {
        try {
            const token = localStorage.getItem("token");
            const res = await axios.get("http://localhost:8080/refugios", {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            setRefuges(res.data);
        } catch (error) {
            console.error("Error al obtener refugios", error);
        }
    };

    useEffect(() => {
        fetchRefuges();
    }, []);

    const handleShowDetails = (refuge) => {
        setSelectedRefuge(refuge);
        setShowModal(true);
    };

    const handleCloseDetails = () => {
        setSelectedRefuge(null);
        setShowModal(false);
    };

    const handleShowDonationModal = (refugeUsername) => {
        setDonationRefuge(refugeUsername);
        setDonationAmount("");
        setShowDonationModal(true);
    };

    const handleSendDonation = async () => {
        try {
            const token = localStorage.getItem('token');
            const response = await fetch("/donaciones", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                },
                body: JSON.stringify({
                    amount: parseFloat(donationAmount),
                    refugeUsername: donationRefuge,
                }),
            });

            if (!response.ok) throw new Error("Error al enviar la donación");

            alert("Donación realizada con éxito. ¡Muchas gracias!");
            setShowDonationModal(false);
        } catch (error) {
            console.error(error);
            alert("Error al procesar la donación");
        }
    };

    return (
        <>
            <Header />
            <div className="container mt-4">
                <h1 className="mb-4">Lista de refugios</h1>
                <div className="row">
                    {refuges.map((refuge) => (
                        <div className="col-md-4 mb-4" key={refuge.username}>
                            <div className="card h-100 shadow-sm">
                                <div className="card-body">
                                    <h5 className="card-title">{refuge.refugeName}</h5>
                                    <p className="card-text">{refuge.description}</p>
                                    <button
                                        className="btn btn-primary me-2"
                                        onClick={() => handleShowDetails(refuge)}
                                    >
                                        Más información
                                    </button>
                                    <button
                                        className="btn btn-success"
                                        onClick={() => handleShowDonationModal(refuge.username)}
                                    >
                                        Donar
                                    </button>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>

                {/* Modal de detalles */}
                <Modal show={showModal} onHide={handleCloseDetails}>
                    <Modal.Header closeButton>
                        <Modal.Title>Detalles del refugio</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        {selectedRefuge && (
                            <>
                                <p><strong>Nombre del refugio:</strong> {selectedRefuge.refugeName}</p>
                                <p><strong>Correo electrónico:</strong> {selectedRefuge.email}</p>
                                <p><strong>Teléfono:</strong> {selectedRefuge.phoneNumber}</p>
                                <p><strong>Descripción:</strong> {selectedRefuge.description}</p>
                                <Button
                                    variant="outline-primary"
                                    href={`mailto:${selectedRefuge.email}`}
                                >
                                    Enviar correo
                                </Button>
                            </>
                        )}
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={handleCloseDetails}>
                            Cerrar
                        </Button>
                    </Modal.Footer>
                </Modal>

                {/* Modal de donación */}
                <Modal show={showDonationModal} onHide={() => setShowDonationModal(false)}>
                    <Modal.Header closeButton>
                        <Modal.Title>Donar al refugio</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Form>
                            <Form.Group controlId="donationAmount">
                                <Form.Label>Monto</Form.Label>
                                <Form.Control
                                    type="number"
                                    placeholder="Ingrese el monto"
                                    value={donationAmount}
                                    onChange={(e) => setDonationAmount(e.target.value)}
                                    min="1"
                                />
                            </Form.Group>
                        </Form>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={() => setShowDonationModal(false)}>
                            Cancelar
                        </Button>
                        <Button variant="success" onClick={handleSendDonation}>
                            Aceptar
                        </Button>
                    </Modal.Footer>
                </Modal>
            </div>

            <Footer />
        </>
    );
};

export default RefugeListPage;
