import { CategoryService, ProductService } from '../services';
import { useEffect, useState } from 'react';
import { Category, Product } from '../types';
import { Box, FormControl, Grid, Pagination, Typography } from '@mui/material';
import ProductCard from './ProductCard';
import { useAppContext } from '../context';
import SelectPaginate from './SelectPaginate';

type Props = {
    shopId: string;
};

const ShopProducts = ({ shopId }: Props) => {
    const { setLoading } = useAppContext();
    const [products, setProducts] = useState<Product[] | null>(null);
    const [count, setCount] = useState<number>(0);
    const [page, setPage] = useState<number>(0);
    const [pageSelected, setPageSelected] = useState<number>(0);
    const [filter, setFilter] = useState<Category | null>(null);

    const getProducts = () => {
        setLoading(true);

        if (filter && filter.name !== 'Toutes les catégories') {
            ProductService.getProductsbyShopAndCategory(shopId, filter.id, pageSelected, 6)
                .then((res) => {
                    setProducts(res.data.content);
                    setCount(res.data.totalPages);
                    setPage(res.data.pageable.pageNumber + 1);
                })
                .catch((err) => console.error(err))
                .finally(() => setLoading(false));
        } else {
            ProductService.getProductsbyShop(shopId, pageSelected, 6)
                .then((res) => {
                    setProducts(res.data.content);
                    setCount(res.data.totalPages);
                    setPage(res.data.pageable.pageNumber + 1);
                })
                .catch((err) => console.error(err))
                .finally(() => setLoading(false));
        }
    };

    useEffect(() => {
        getProducts();
    }, [shopId, pageSelected, filter]);

    const handleChangePagination = (event: React.ChangeEvent<unknown>, value: number) => {
        setPageSelected(value - 1);
    };

    return (
        <Box sx={{ width: '100%', display: 'flex', flexDirection: 'column', alignItems: 'center', gap: 5 }}>
            <Box sx={{ width: '100%', display: 'flex', flexDirection: 'row', justifyContent: 'space-between' }}>
                <FormControl sx={{ minWidth: 220 }}>
                    <SelectPaginate
                        value={filter}
                        onChange={setFilter}
                        placeholder="Catégorie"
                        refetch={CategoryService.getCategories}
                        defaultLabel="Toutes les catégories"
                    />
                </FormControl>
            </Box>

            <Grid container alignItems="center" rowSpacing={3} columnSpacing={3}>
                {products?.map((product) => (
                    <Grid item key={product.id} xs={4}>
                        <ProductCard product={product} />
                    </Grid>
                ))}
            </Grid>

            {products && products.length > 0 ? (
                <Pagination count={count} page={page} siblingCount={1} onChange={handleChangePagination} />
            ) : (
                <Typography variant="h6" sx={{ mt: -4 }}>
                    Aucun produit correspondant
                </Typography>
            )}
        </Box>
    );
};

export default ShopProducts;
