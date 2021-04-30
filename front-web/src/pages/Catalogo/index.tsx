import React from 'react';
import ProductCard from './components/ProductCard';
import './styles.scss';

const Catalogo = () =>(
    <div className="catalog-container">
        <h1 className="catalog-title">
            Catalogo de produtos
        </h1>
        <div className="catalog-products">
            <ProductCard />
            <ProductCard />
            <ProductCard />
            <ProductCard />
            <ProductCard />
            <ProductCard />
            <ProductCard />
            <ProductCard />
            <ProductCard />
            <ProductCard />
            <ProductCard />
            <ProductCard />
        </div>
    </div>
   
)

export default Catalogo;