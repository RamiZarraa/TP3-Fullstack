import axios, { AxiosResponse } from 'axios';
import { MinimalProduct, Product } from '../types';

interface ProductResponse {
    content: Product[];
    pageable: {
        pageNumber: number;
        pageSize: number;
        offset: number;
    };
    totalPages: number;
    totalElements: number;
    last: boolean;
    first: boolean;
    size: number;
    number: number;
    numberOfElements: number;
    empty: boolean;
}

const API_URL = process.env.REACT_APP_API || 'http://localhost:8080/api/v1';

export function getProducts(
    page: number,
    size: number,
    search?: string,
    sortBy?: string,
    sortDirection?: 'asc' | 'desc'
): Promise<AxiosResponse<ProductResponse>> {
    const params = new URLSearchParams({
        page: page.toString(),
        size: size.toString(),
    });

    if (search) params.append('search', search);
    if (sortBy) params.append('sortBy', sortBy);
    if (sortDirection) params.append('sortDirection', sortDirection);

    return axios.get(`${API_URL}/products?${params}`);
}

export function getProductsbyShop(shopId: string, page: number, size: number): Promise<AxiosResponse<ProductResponse>> {
    return axios.get(`${API_URL}/products?shopId=${shopId}&page=${page}&size=${size}`);
}

export function getProductsbyShopAndCategory(
    shopId: string,
    categoryId: number,
    page: number,
    size: number,
): Promise<AxiosResponse<ProductResponse>> {
    return axios.get(`${API_URL}/products?shopId=${shopId}&categoryId=${categoryId}&page=${page}&size=${size}`);
}

export function getProduct(id: string): Promise<AxiosResponse<Product>> {
    return axios.get(`${API_URL}/products/${id}`);
}

export function createProduct(product: MinimalProduct): Promise<AxiosResponse<Product>> {
    return axios.post(`${API_URL}/products`, product);
}

export function editProduct(id: string, product: MinimalProduct): Promise<AxiosResponse<Product>> {
    return axios.put(`${API_URL}/products/${id}`, product);
}

export function deleteProduct(id: string): Promise<AxiosResponse<void>> {
    return axios.delete(`${API_URL}/products/${id}`);
}