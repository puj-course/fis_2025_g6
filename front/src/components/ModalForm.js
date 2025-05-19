import React, { useState } from "react";

const ModalForm = ({ show, onClose, petId, onSubmit }) => {
    const [formData, setFormData] = useState({
        hasPets: false,
        hasRoom: false,
        housingType: "",
        hoursAwayFromHome: 0,
        vaccinationCommitment: false,
        previousExperience: "",
    });

    const handleChange = (e) => {
        const { name, type, value, checked } = e.target;
        setFormData((prev) => ({
            ...prev,
            [name]: type === "checkbox" ? checked : value,
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        onSubmit(petId, formData); // El padre se encarga de crear solicitud y formulario
        onClose();
    };

    if (!show) return null;

    return (
        <div className="modal show d-block" tabIndex="-1">
            <div className="modal-dialog">
                <div className="modal-content">
                    <form onSubmit={handleSubmit}>
                        <div className="modal-header">
                            <h5 className="modal-title">Formulario de Adopción</h5>
                            <button type="button" className="btn-close" onClick={onClose} />
                        </div>
                        <div className="modal-body">
                            <div className="form-check">
                                <input
                                    type="checkbox"
                                    name="hasPets"
                                    className="form-check-input"
                                    checked={formData.hasPets}
                                    onChange={handleChange}
                                />
                                <label className="form-check-label">¿Tienes otras mascotas?</label>
                            </div>

                            <div className="form-check">
                                <input
                                    type="checkbox"
                                    name="hasRoom"
                                    className="form-check-input"
                                    checked={formData.hasRoom}
                                    onChange={handleChange}
                                />
                                <label className="form-check-label">¿Tienes espacio suficiente?</label>
                            </div>

                            <div className="mb-3">
                                <label className="form-label">Tipo de vivienda</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    name="housingType"
                                    value={formData.housingType}
                                    onChange={handleChange}
                                />
                            </div>

                            <div className="mb-3">
                                <label className="form-label">Horas fuera de casa</label>
                                <input
                                    type="number"
                                    className="form-control"
                                    name="hoursAwayFromHome"
                                    value={formData.hoursAwayFromHome}
                                    onChange={handleChange}
                                />
                            </div>

                            <div className="form-check">
                                <input
                                    type="checkbox"
                                    name="vaccinationCommitment"
                                    className="form-check-input"
                                    checked={formData.vaccinationCommitment}
                                    onChange={handleChange}
                                />
                                <label className="form-check-label">
                                    ¿Te comprometes a vacunar a la mascota?
                                </label>
                            </div>

                            <div className="mb-3">
                                <label className="form-label">Experiencia previa con mascotas</label>
                                <textarea
                                    className="form-control"
                                    name="previousExperience"
                                    value={formData.previousExperience}
                                    onChange={handleChange}
                                />
                            </div>
                        </div>
                        <div className="modal-footer">
                            <button className="btn btn-secondary" onClick={onClose}>
                                Cancelar
                            </button>
                            <button type="submit" className="btn btn-primary">
                                Enviar
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
};

export default ModalForm;
