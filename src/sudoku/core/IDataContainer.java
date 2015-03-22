
package sudoku.core;

public interface IDataContainer {

	public String getOriginalData(int line, int column);

	public void shuffleData();

	public void setData(int line, int column, String data);

	public void setFinishedListener(IFinishedListener listener);

	public void setFieldListener(IFieldListener listener);

	public void abortWork();
}
