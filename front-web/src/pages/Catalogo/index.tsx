import React from 'react';
import { Link } from 'react-router-dom';
import ProductCard from './components/ProductCard';
import './styles.scss';

const Catalogo = () =>(
    <div className="catalog-container">
        <h1 className="catalog-title">
            Catalogo de produtos
        </h1>
        <div className="catalog-products">
            <Link to="/products/1"><ProductCard /></ Link>
            <Link to="/products/2"><ProductCard /></ Link>
            <Link to="/products/3"><ProductCard /></ Link>
            <Link to="/products/4"><ProductCard /></ Link>
            <Link to="/products/6"><ProductCard /></ Link>
            <Link to="/products/7"><ProductCard /></ Link>
            <Link to="/products/8"><ProductCard /></ Link>
            <Link to="/products/9"><ProductCard /></ Link>
            <Link to="/products/10"><ProductCard /></ Link>
            <Link to="/products/11"><ProductCard /></ Link>
            <Link to="/products/12"><ProductCard /></ Link>
            <Link to="/products/13"><ProductCard /></ Link>
        </div>
    </div>
   
)

export default Catalogo;