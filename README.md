BlockingTests
=============

Tests for threading in Android, for DIMA @ Polimi 2014

The app has 4 tabs:
* the first one executes a *long* computation (represented as a Thread.sleep() call) directly in the UI thread
* the second one executes the same computation in a parallel thread, trying to modify a View from that thread (it is expected to receive an Exception)
* the third one executes the computation in a parallel thread and updates the view through View.post(Runnable)
* the last one executes the computation using an AsyncTask.
