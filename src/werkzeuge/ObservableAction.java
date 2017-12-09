package werkzeuge;

import javafx.event.ActionEvent;

public interface ObservableAction
{

	public void addListener(ActionListener listener);

	public void removeListener(ActionListener listener);

	void notifyListeners(ActionEvent event);
}
