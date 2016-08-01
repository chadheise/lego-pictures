package heise.chad.lego.generic.transform;

import java.util.ArrayList;
import java.util.List;

public final class CompositeTransform<T> implements Transform<T> {

	private List<Transform<T>> transforms;

	private CompositeTransform(List<Transform<T>> transforms) {
		this.transforms = transforms;
	}

	@Override
	public T transform(T obj) {
		T output = obj;
		for (Transform<T> transform : transforms) {
			output = transform.transform(output);
		}
		return output;
	}

	public static final class Builder<T> {
		List<Transform<T>> builderTransforms = new ArrayList<Transform<T>>();

		public Builder<T> withTransform(Transform<T> transform) {
			builderTransforms.add(transform);
			return this;
		}

		public CompositeTransform<T> build() {
			return new CompositeTransform<T>(builderTransforms);
		}

	}

}
