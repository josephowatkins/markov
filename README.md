# markov

Simple markov chains in clojure

# Usage

```clojure
(require '[markov.core :refer :all])

(def chains (-> (read-file) build-chains))

(new-verse chains)
;; "Stolen waters are sweet, and bread eaten in secret is pleasant."
```

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
