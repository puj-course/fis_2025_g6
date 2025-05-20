export const translatePetStatus = (status) => {
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
};

export const translateApplicationStatus = (status) => {
    switch (status) {
        case 'PENDING':
            return 'Disponible';
        case 'APPROVED':
            return 'En proceso';
        case 'REJECTED':
            return 'Adoptado';
        case 'CANCELED':
            return 'Cancelado';
        default:
            return '';
    }
};
