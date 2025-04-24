import { render, screen, within } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import CountryDetail from '../CountryDetail';

describe('CountryDetail', () => {
  const mockCountry = {
    name: 'Testland',
    capital: 'Testville',
    population: 1000000,
    flag: 'test-flag.png',
  };

  it('renders country details correctly', () => {
    render(
      <MemoryRouter>
        <CountryDetail country={mockCountry} />
      </MemoryRouter>
    );

    expect(screen.getByText('Testland')).toBeInTheDocument();

    const capitalPara = screen.getByText((_, element) =>
      element.tagName.toLowerCase() === 'p' &&
      element.textContent.includes('Capital:')
    );
    expect(capitalPara.textContent).toMatch(/Capital:\s*Testville/);

    const populationPara = screen.getByText((_, element) =>
      element.tagName.toLowerCase() === 'p' &&
      element.textContent.includes('Population:')
    );
    expect(populationPara.textContent).toMatch(/Population:\s*1.?000.?000/);

    expect(screen.getByAltText('Flag of Testland')).toHaveAttribute('src', 'test-flag.png');
  });

  it('has a back button', () => {
    render(
      <MemoryRouter>
        <CountryDetail country={mockCountry} />
      </MemoryRouter>
    );

    expect(screen.getByText('Back')).toBeInTheDocument();
  });
});
