package fechten;

import static org.junit.Assert.*;

import org.junit.Test;

public class ErgebnisTest
{
	private Tableau tablo;
	
	public ErgebnisTest()
	{
		tablo = new Tableau(10, 5);
	}

	@Test
	public void testKonstruktor()
	{
		Ergebnis e = new Ergebnis(Ergebniszustand.AUSSTEHEND, 5, tablo);
		assertEquals(tablo, e.getTablo());
		assertEquals(0, e.getTreffer());
		assertFalse(e.hatGewonnen());
		assertEquals(Ergebniszustand.AUSSTEHEND, e.getZustand());
		assertEquals("-",e.toString());
		
		e = new Ergebnis(Ergebniszustand.UNGUELTIG, 5, null);
		
		assertEquals(null, e.getTablo());
		assertEquals(0, e.getTreffer());
		assertFalse(e.hatGewonnen());
		assertEquals(Ergebniszustand.UNGUELTIG, e.getZustand());
		assertEquals("X",e.toString());
	}
	
	@Test
	public void testEintragen()
	{
		Ergebnis e = new Ergebnis(Ergebniszustand.AUSSTEHEND, 5, tablo);
		e.eintragen(3, false);
		assertEquals(3, e.getTreffer());
		assertFalse(e.hatGewonnen());
		assertEquals("3", e.toString());
		assertEquals(Ergebniszustand.GEFOCHTEN, e.getZustand());
		
		e = new Ergebnis(Ergebniszustand.AUSSTEHEND, 5, tablo);
		e.eintragen(5, true);
		assertEquals(5, e.getTreffer());
		assertTrue(e.hatGewonnen());
		assertEquals("V5", e.toString());
	}

}
