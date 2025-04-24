import { render, screen } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import CountryGrid from '../CountryGrid';

describe('CountryGrid', () => {
  const mockCountries = [
    { name: 'Testland', flag: 'test-flag.png' },
    { name: 'Mockland', flag: 'mock-flag.png' },
  ];

  it('renders all country cards', () => {
    render(
      <MemoryRouter>
        <CountryGrid countries={mockCountries} />
      </MemoryRouter>
    );

    expect(screen.getByText('Testland')).toBeInTheDocument();
    expect(screen.getByText('Mockland')).toBeInTheDocument();
    expect(screen.getAllByRole('img')).toHaveLength(2);
  });

  it('renders empty state when no countries', () => {
    render(
      <MemoryRouter>
        <CountryGrid countries={[]} />
      </MemoryRouter>
    );

    expect(screen.queryByRole('img')).not.toBeInTheDocument();
  });
});
