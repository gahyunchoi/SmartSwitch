
from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

with open('/Users/gahyunchoi/Desktop/onoff2/bin/activate_this.py', 'r') as f:
	code = compile(f.read(), '/Users/gahyunchoi/Desktop/onoff2/bin/activate_this.py', 'exec')
	exec(code)




import os
import urllib

import numpy as np
import tensorflow as tf

# Data sets
IRIS_TRAINING = "training10.csv"

IRIS_TEST = "test10.csv"

def main():


  # Load datasets.
  training_set = tf.contrib.learn.datasets.base.load_csv_with_header(
      filename=IRIS_TRAINING,
      target_dtype=np.int,
      features_dtype=np.float32)
  test_set = tf.contrib.learn.datasets.base.load_csv_with_header(
      filename=IRIS_TEST,
      target_dtype=np.int,
      features_dtype=np.float32)

  # Specify that all features have real-value data
  feature_columns = [tf.feature_column.numeric_column("x", shape=[4])]

  # Build 3 layer DNN with 10, 20, 10 units respectively.
  classifier = tf.estimator.DNNClassifier(feature_columns=feature_columns,
                                          hidden_units=[10, 20, 10],
                                          n_classes=3,
                                          model_dir="/tmp/iris_model")
  # Define the training inputs
  train_input_fn = tf.estimator.inputs.numpy_input_fn(
      x={"x": np.array(training_set.data)},
      y=np.array(training_set.target),
      num_epochs=None,
      shuffle=True)

  # Train model.
  classifier.train(input_fn=train_input_fn, steps=2000)

  # Define the test inputs
  test_input_fn = tf.estimator.inputs.numpy_input_fn(
      x={"x": np.array(test_set.data)},
      y=np.array(test_set.target),
      num_epochs=1,
      shuffle=False)

  # Evaluate accuracy.
  accuracy_score = classifier.evaluate(input_fn=test_input_fn)["accuracy"]

  print("\n고데기일 확률: {0:f}% \n".format(accuracy_score*100))

  # Classify two new flower samples.
  new_samples = np.array(
      [[68,11,0,0]], dtype=np.float32)
  predict_input_fn = tf.estimator.inputs.numpy_input_fn(
      x={"x": new_samples},
      num_epochs=1,
      shuffle=False)

  predictions = list(classifier.predict(input_fn=predict_input_fn))
  predicted_classes = [p["classes"] for p in predictions]

  print(
      "이 전자기기의 Type은 {}\n"
      .format(predicted_classes))

if __name__ == "__main__":
    main()

