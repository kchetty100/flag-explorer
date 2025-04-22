import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { getCountryDetails } from '../services/api';
import CountryDetail from '../components/CountryDetail';

const DetailPage = () => {
  const { name } = useParams();
  const [country, setCountry] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchCountryDetails = async () => {
      const data = await getCountryDetails(name);
      setCountry(data);
      setLoading(false);
    };

    fetchCountryDetails();
  }, [name]);

  if (loading) return <div>Loading country details...</div>;
  if (!country) return <div>Country not found</div>;

  return <CountryDetail country={country} />;
};

export default DetailPage;