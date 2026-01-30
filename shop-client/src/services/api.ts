import axios from 'axios';

const api = axios.create({
    baseURL: process.env.REACT_APP_API || 'http://localhost:8080/api/v1',
    headers: {
        'Content-Type': 'application/json',
    },
});

export default api;