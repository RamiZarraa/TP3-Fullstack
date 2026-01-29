import {
    Box,
    Fab,
    FormControl,
    Grid,
    InputLabel,
    MenuItem,
    Pagination,
    Select,
    SelectChangeEvent,
    Typography,
    TextField,
}  from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import SearchIcon from '@mui/icons-material/Search';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Filters, ShopCard } from '../components';
import { useAppContext } from '../context';
import { ShopService } from '../services';
import { ResponseArray, Shop } from '../types';

const Home = () => {
    const navigate = useNavigate();
    const { setLoading } = useAppContext();
    const [shops, setShops] = useState<Shop[] | null>(null);
    const [count, setCount] = useState<number>(0);
    const [page, setPage] = useState<number>(0);
    const [pageSelected, setPageSelected] = useState<number>(0);

    const [searchQuery, setSearchQuery] = useState<string>('');
    const [sort, setSort] = useState<string>('');
    const [filters, setFilters] = useState<string>('');

    const getShops = () => {
        setLoading(true);
        
        // Parse filters from URL string
        const params = new URLSearchParams(filters);
        const inVacations = params.get('inVacations') || undefined;
        const createdAfter = params.get('createdAfter') || undefined;
        
        // Use Elasticsearch search
        ShopService.searchShops(
            pageSelected,
            9,
            searchQuery || undefined,
            inVacations,
            createdAfter,
            undefined, // createdBefore not supported
            undefined, // openOn
            sort || undefined
        )
            .then((res) => {
                setShops(res.data.content);
                setCount(res.data.totalPages);
                setPage(res.data.pageable.pageNumber + 1);
            })
            .finally(() => setLoading(false));
    };

    useEffect(() => {
        getShops();
    }, [pageSelected, sort, filters, searchQuery]);

    const handleChangePagination = (event: React.ChangeEvent<unknown>, value: number) => {
        setPageSelected(value - 1);
    };

    const handleChangeSort = (event: SelectChangeEvent) => {
        setSort(event.target.value as string);
    };

    const handleSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setSearchQuery(event.target.value);
        setPageSelected(0); // Reset to first page on search
    };

    return (
        <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center', gap: 5 }}>
            <Typography variant="h2">Les boutiques</Typography>

            <Box
                sx={{
                    width: '100%',
                    display: 'flex',
                    flexDirection: 'row',
                    justifyContent: 'flex-end',
                }}
            >
                <Fab variant="extended" color="primary" aria-label="add" onClick={() => navigate('/shop/create')}>
                    <AddIcon sx={{ mr: 1 }} />
                    Ajouter une boutique
                </Fab>
            </Box>

            {/* Search bar */}
            <Box sx={{ width: '100%' }}>
                <TextField
                    fullWidth
                    label="Rechercher une boutique"
                    placeholder="Ex: Boutique, bout, butique..."
                    value={searchQuery}
                    onChange={handleSearchChange}
                    InputProps={{
                        endAdornment: <SearchIcon />,
                    }}
                />
            </Box>

            {/* Sort and filters */}
            <Box
                sx={{
                    width: '100%',
                    display: 'flex',
                    flexDirection: 'row',
                    justifyContent: 'space-between',
                }}
            >
                <FormControl sx={{ minWidth: 200 }}>
                    <InputLabel id="demo-simple-select-label">Trier par</InputLabel>
                    <Select
                        labelId="demo-simple-select-label"
                        id="demo-simple-select"
                        value={sort}
                        label="Trier par"
                        onChange={handleChangeSort}
                    >
                        <MenuItem value="">
                            <em>Aucun</em>
                        </MenuItem>
                        <MenuItem value="name,asc">Nom (A → Z)</MenuItem>
                        <MenuItem value="name,desc">Nom (Z → A)</MenuItem>
                        <MenuItem value="createdAt,desc">Date de création (récent → ancien)</MenuItem>
                        <MenuItem value="createdAt,asc">Date de création (ancien → récent)</MenuItem>
                        <MenuItem value="nbProducts,desc">Nombre de produits (plus → moins)</MenuItem>
                        <MenuItem value="nbProducts,asc">Nombre de produits (moins → plus)</MenuItem>
                    </Select>
                </FormControl>

                <Filters setUrlFilters={setFilters} setSort={setSort} sort={sort} />
            </Box>

            {/* Shops */}
            <Grid container spacing={3}>
                {shops?.map((shop) => (
                    <Grid item key={shop.id} xs={12} sm={6} md={4}>
                        <ShopCard shop={shop} />
                    </Grid>
                ))}
            </Grid>

            {/* Pagination */}
            {shops?.length !== 0 ? (
                <Pagination count={count} page={page} siblingCount={1} onChange={handleChangePagination} />
            ) : (
                <Typography variant="h5" sx={{ mt: -1 }}>
                    Aucune boutique correspondante
                </Typography>
            )}
        </Box>
    );
};

export default Home;