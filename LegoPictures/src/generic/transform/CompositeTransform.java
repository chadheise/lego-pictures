package generic.transform;

import generic.color.grid.ColorGrid;

import java.util.List;


public class CompositeTransform implements ColorGridTransform {

	List<ColorGridTransform> transforms;
	
	public CompositeTransform(List<ColorGridTransform> transforms) {
		this.transforms = transforms;
	}
	
	@Override
	public ColorGrid transform(ColorGrid colorGrid) {
		ColorGrid output = colorGrid;
		for (ColorGridTransform transform : transforms) {
			output = transform.transform(output);
		}
		return output;
	}

}
