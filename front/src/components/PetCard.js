import React from "react";
import { translatePetStatus } from '../util/Util';

const PetCard = ({ pet, hasActiveApplication, onApply, onShowRefuge }) => {
    return (
        <div className="col-md-4 mb-4">
            <div className="card h-100">
                <div className="card-body">
                    <h5 className="card-title">{pet.name}</h5>
                    <p className="card-text"><strong>ID:</strong> {pet.id}</p>
                    <p className="card-text"><strong>Especie:</strong> {pet.species}</p>
                    <p className="card-text"><strong>Edad:</strong> {pet.age}</p>
                    <p className="card-text"><strong>Sexo:</strong> {pet.sex}</p>
                    <p className="card-text"><strong>Estado:</strong> {translatePetStatus(pet.status)}</p>
                    <p className="card-text">{pet.description}</p>

                    {pet.refuge && (
                        <div className="mt-2 d-flex gap-2">
                            <button
                                className="btn btn-outline-primary"
                                onClick={() => onShowRefuge(pet.refuge)}
                            >
                                Ver refugio
                            </button>
                            <button
                                className={`btn ${hasActiveApplication(pet.id) ? 'btn-secondary' : 'btn-success'}`}
                                onClick={() => onApply(pet.id)}
                                disabled={hasActiveApplication(pet.id)}
                            >
                                {hasActiveApplication(pet.id) ? 'Ya aplicado' : 'Aplicar'}
                            </button>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
};

export default PetCard;
