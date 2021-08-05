import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { makeRequest } from 'core/utils/request';
import { ProductResponse } from 'core/types/Product';
import ProductCard from './components/ProductCard';
import './styles.scss';
import ProductCardLoader from './components/Loaders/ProductCardLoader';

const Catalogo = () => {

    const [productsResponse, setProducsResponse] = useState<ProductResponse>();
    const [isLoading, setIsLoading] = useState(false);

    console.log(productsResponse);

    useEffect(() => {
        const params = {
            page: 0,
            linesPerPage: 10
        }
        setIsLoading(true);
        makeRequest({ url: '/products', params })
            .then(response => setProducsResponse(response.data))
            .finally(() => {
                setIsLoading(false);
            })
    }, []);

    return (
        <div className="catalog-container">
            <h1 className="catalog-title">
                Catalogo de produtos
            </h1>
            <div className="catalog-products">
                {isLoading ? <ProductCardLoader /> : (
                    productsResponse?.content
                        .map(
                            product => (
                                <Link to={`/products/${product.id}`} key={product.id}>
                                    <ProductCard product={product} />
                                </ Link>
                            )
                        )
                )}
            </div>
        </div>

    )
};

export default Catalogo;