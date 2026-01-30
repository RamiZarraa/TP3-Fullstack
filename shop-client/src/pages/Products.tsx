import {
    Box,
    Fab,
    Grid,
    Pagination,
    Typography,
    FormControl,
    InputLabel,
    Select,
    MenuItem,
    SelectChangeEvent,
    TextField,
    Button,
} from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import SearchIcon from '@mui/icons-material/Search';
import RestartAltIcon from '@mui/icons-material/RestartAlt';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ProductCard } from '../components';
import { useAppContext } from '../context';
import { ProductService } from '../services';
import { Product } from '../types';

const Products = () => {
    const navigate = useNavigate();
    const { setLoading } = useAppContext();
    const [products, setProducts] = useState<Product[] | null>(null);
    const [count, setCount] = useState<number>(0);
    const [page, setPage] = useState<number>(0);
    const [pageSelected, setPageSelected] = useState<number>(0);
    const [sort, setSort] = useState<string>('');
    const [search, setSearch] = useState<string>('');
    const [searchInput, setSearchInput] = useState<string>('');

    const getProducts = () => {
        setLoading(true);

        const [sortBy, sortDirection] = sort ? sort.split(',') : ['id', 'asc'];

        ProductService.getProducts(pageSelected, 9, search || undefined, sortBy, sortDirection as 'asc' | 'desc')
            .then((res) => {
                setProducts(res.data.content);
                setCount(res.data.totalPages);
                setPage(res.data.pageable.pageNumber + 1);
            })
            .catch((err) => {
                console.error('Erreur chargement produits:', err);
            })
            .finally(() => setLoading(false));
    };

    useEffect(() => {
        getProducts();
    }, [pageSelected, sort, search]);

    const handleChangePagination = (event: React.ChangeEvent<unknown>, value: number) => {
        setPageSelected(value - 1);
    };

    const handleChangeSort = (event: SelectChangeEvent) => {
        setSort(event.target.value as string);
    };

    const handleSearch = () => {
        setSearch(searchInput);
        setPageSelected(0);
    };

    const handleReset = () => {
        setSearchInput('');
        setSearch('');
        setSort('');
        setPageSelected(0);
    };

    const handleKeyPress = (e: React.KeyboardEvent) => {
        if (e.key === 'Enter') {
            handleSearch();
        }
    };

    return (
        <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center', gap: 3, p: 3 }}>
            <Typography variant="h2">Les produits</Typography>

            <Box sx={{ width: '100%', display: 'flex', flexDirection: 'row', justifyContent: 'flex-end', mb: 2 }}>
                <Fab variant="extended" color="primary" aria-label="add" onClick={() => navigate('/product/create')}>
                    <AddIcon sx={{ mr: 1 }} />
                    Ajouter un produit
                </Fab>
            </Box>

            <Box
                sx={{
                    width: '100%',
                    display: 'flex',
                    flexDirection: 'row',
                    gap: 2,
                    flexWrap: 'wrap',
                    alignItems: 'center',
                    mb: 3,
                }}
            >
                <TextField
                    label="Rechercher un produit"
                    variant="outlined"
                    value={searchInput}
                    onChange={(e) => setSearchInput(e.target.value)}
                    onKeyPress={handleKeyPress}
                    sx={{ flex: 1, minWidth: 250 }}
                />

                <FormControl sx={{ minWidth: 200 }}>
                    <InputLabel>Trier par</InputLabel>
                    <Select value={sort} label="Trier par" onChange={handleChangeSort}>
                        <MenuItem value="">
                            <em>Aucun</em>
                        </MenuItem>
                        <MenuItem value="price,asc">Prix (croissant)</MenuItem>
                        <MenuItem value="price,desc">Prix (décroissant)</MenuItem>
                        <MenuItem value="id,asc">ID (croissant)</MenuItem>
                        <MenuItem value="id,desc">ID (décroissant)</MenuItem>
                    </Select>
                </FormControl>

                <Button
                    variant="contained"
                    color="primary"
                    startIcon={<SearchIcon />}
                    onClick={handleSearch}
                    sx={{ height: 56 }}
                >
                    Rechercher
                </Button>

                <Button
                    variant="outlined"
                    color="secondary"
                    startIcon={<RestartAltIcon />}
                    onClick={handleReset}
                    sx={{ height: 56 }}
                >
                    Réinitialiser
                </Button>
            </Box>

            <Grid container spacing={3}>
                {products?.map((product) => (
                    <Grid item key={product.id} xs={12} sm={6} md={4}>
                        <ProductCard product={product} displayShop={true} />
                    </Grid>
                ))}
            </Grid>

            {products && products.length > 0 ? (
                <Pagination count={count} page={page} siblingCount={1} onChange={handleChangePagination} />
            ) : (
                <Typography variant="h5" sx={{ mt: -1 }}>
                    Aucun produit correspondant
                </Typography>
            )}
        </Box>
    );
};

export default Products;
