import { MinimalShop } from './../types/shop';
import axios, { AxiosResponse } from 'axios';
import { Shop } from '../types';
import { ResponseArray } from '../types/response';

export function searchShops(
    page: number,
    size: number,
    query?: string,
    inVacations?: string,
    createdAfter?: string,
    createdBefore?: string,
    openOn?: number,
    sort?: string
): Promise<ResponseArray<Shop>> {
    let url = `${process.env.REACT_APP_API}/shops/search?page=${page}&size=${size}`;
    
    if (query) url += `&query=${encodeURIComponent(query)}`;
    if (inVacations) url += `&inVacations=${inVacations}`;
    if (createdAfter) url += `&createdAfter=${createdAfter}`;
    if (openOn) url += `&openOn=${openOn}`;
    if (sort) url += `&sort=${sort}`;
    
    return axios.get(url);
}
export function getShops(page: number, size: number): Promise<ResponseArray<Shop>> {
    return axios.get(`${process.env.REACT_APP_API}/shops?page=${page}&size=${size}`);
}

export function getShopsSorted(page: number, size: number, sort: string): Promise<ResponseArray<Shop>> {
    return axios.get(`${process.env.REACT_APP_API}/shops?page=${page}&size=${size}&sortBy=${sort}`);
}

export function getShopsFiltered(page: number, size: number, urlFilters: string): Promise<ResponseArray<Shop>> {
    return axios.get(`${process.env.REACT_APP_API}/shops?page=${page}&size=${size}${urlFilters}`);
}

export function getShop(id: string): Promise<AxiosResponse<Shop>> {
    return axios.get(`${process.env.REACT_APP_API}/shops/${id}`);
}

export function createShop(shop: MinimalShop): Promise<AxiosResponse<Shop>> {
    return axios.post(`${process.env.REACT_APP_API}/shops`, shop);
}

export function editShop(shop: MinimalShop): Promise<AxiosResponse<Shop>> {
    return axios.put(`${process.env.REACT_APP_API}/shops`, shop);
}

export function deleteShop(id: string): Promise<AxiosResponse<Shop>> {
    return axios.delete(`${process.env.REACT_APP_API}/shops/${id}`);
}
