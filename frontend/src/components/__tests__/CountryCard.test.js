import { render, screen } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import CountryCard from '../CountryCard';

describe('CountryCard', () => {
  const mockCountry = {
    name: 'Testland',
    flag: 'test-flag.png',
  };

  it('renders country name and flag', () => {
    render(
      <MemoryRouter>
        <CountryCard country={mockCountry} />
      </MemoryRouter>
    );

    expect(screen.getByText('Testland')).toBeInTheDocument();
    expect(screen.getByAltText('Flag of Testland')).toBeInTheDocument();
  });
});
