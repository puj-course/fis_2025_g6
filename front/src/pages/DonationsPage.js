import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Header from '../components/Header';
import Footer from '../components/Footer';

export default function DonationsPage() {
    const [donations, setDonations] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchDonations = async () => {
            try {
                const token = localStorage.getItem('token');
                const response = await axios.get('http://localhost:8080/donaciones/me', {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                setDonations(response.data);
            } catch (err) {
                console.error(err);
                setError('Error al cargar las donaciones');
            } finally {
                setLoading(false);
            }
        };

        fetchDonations();
    }, []);

    if (loading) return <div className="container mt-4">Cargando donaciones...</div>;
    if (error) return <div className="container mt-4 text-danger">{error}</div>;

    return (
        <>
            <Header />
            <div className="container mt-4">
                <h1>Donaciones recibidas</h1>
                {donations.length === 0 ? (
                    <p>No se han recibido donaciones.</p>
                ) : (
                    <div className="table-responsive">
                        <table className="table table-striped">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Donante</th>
                                    <th>Correo</th>
                                    <th>Monto</th>
                                    <th>Fecha</th>
                                </tr>
                            </thead>
                            <tbody>
                                {donations.map((donation, index) => (
                                    <tr key={donation.id}>
                                        <td>{index + 1}</td>
                                        <td>{donation.adoptant.adoptantName}</td>
                                        <td>{donation.adoptant.email}</td>
                                        <td>${donation.amount.toFixed(2)}</td>
                                        <td>{donation.date}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                )}
            </div>
            <Footer />
        </>
    );
} 
