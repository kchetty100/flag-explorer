import React from 'react';
import CountryCard from './CountryCard';

const CountryGrid = ({ countries }) => {
  return (
    <div className="country-grid">
      {countries.map((country) => (
        <CountryCard key={country.name} country={country} />
      ))}
    </div>
  );
};

export default CountryGrid;