package service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fechten.Fechter;

public class GroessterVereinComperator implements Comparator<Fechter>
{
	Map<String, Integer> _vereinsHaefigkeit;
	
	public GroessterVereinComperator(List<Fechter> fechter)
	{
		_vereinsHaefigkeit = new HashMap<>();
		for(Fechter f: fechter)
		{
			if(_vereinsHaefigkeit.containsKey(f.vereinProperty().get()))
			{
				_vereinsHaefigkeit.put(f.vereinProperty().get(), 1 + _vereinsHaefigkeit.get(f.vereinProperty().get()));
			}
			else
			{
				_vereinsHaefigkeit.put(f.vereinProperty().get(), 1);
			}
		}
	}
	
	
	@Override
	public int compare(Fechter o1, Fechter o2)
	{
		int i1 = _vereinsHaefigkeit.get(o1.vereinProperty().get());
		int i2 = _vereinsHaefigkeit.get(o2.vereinProperty().get());
		
		return i2 -i1;
	}

}
