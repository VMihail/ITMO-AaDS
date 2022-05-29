#include <algorithm>
#include <array>
#include <bitset>
#include <cassert>
#include <climits>
#include <cstdint>
#include <cmath>
#include <complex>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <functional>
#include <iomanip>
#include <iostream>
#include <map>
#include <memory>
#include <numeric>
#include <queue>
#include <random>
#include <set>
#include <stack>
#include <string>
#include <unordered_set>
#include <unordered_map>
#include <ctime>
#include <vector>

#define int int32_t

typedef int64_t int64;
typedef uint64_t uint64;

using namespace std;

class ArraysUtils {
public:
    template <typename T>
    static void printArray(const vector<T>& array) {
      for (auto el : array) {
        cout << el << ' ';
      }
      cout << '\n';
    }

    template <typename T>
    static vector<T> nextArray(size_t len) {
      vector<T> result(len);
      for (size_t i = 0; i < len; i++) {
        cin >> result[i];
      }
      return result;
    }
};

namespace Utils {
    int64 max(int64 a, int64 b, int64 c) {
      return std::max(a, std::max(b, c));
    }

    int64 max(int64 a, int64 b) {
      return std::max(a, b);
    }
}

struct Node {
    int value;

    Node(int value) {
      this->value = value;
    }

    static Node merge(const Node &first, const Node &second) {
      using namespace Utils;
      return {max(first.value,  second.value)};
    }

    static Node init(int value) {
      return {value};
    }
};

namespace SegTree {
    class SegmentTree {
    private:
        vector<Node> tree;
        size_t size;
        Node NEUTRAL = Node(0);
        Node leftAccumulator = NEUTRAL, rightAccumulator = NEUTRAL;

        void init(size_t n) {
          size = 1;
          while (size < n) {
            size <<= 1;
          }
          tree.assign((size << 1) - 1, NEUTRAL);
        }

        size_t getMiddle(size_t left, size_t right) {
          return ((right - left) >> 1) + left;
        }

        Node merge(Node &first, Node &second) {
          return Node::merge(first, second);
        }

        void build(const vector<int>& array, size_t node, size_t left, size_t right) {
          if (right == left + 1) {
            if (node < tree.size() and left < array.size()) {
              tree[node] = Node::init(array[left]);
            }
            return;
          }
          auto middle = getMiddle(left, right);
          auto child = (node << 1) + 1;
          build(array, child, left, middle);
          build(array, child + 1, middle, right);
          tree[node] = merge(tree[child], tree[child + 1]);
        }

        void build(const vector<int>& array) {
          build(array, 0, 0, size);
        }
    public:
        explicit SegmentTree(const vector<int>& array) {
          init(array.size());
          build(array);
        }

        explicit SegmentTree(size_t n) {
          init(n);
        }

        void set(size_t i, int value) {
          size_t index = size - 1 + i;
          tree[index] = Node::init(value);
          while (index > 0) {
            index = (index - 1) >> 1;
            size_t child = (index << 1) + 1;
            tree[index] = merge(tree[child], tree[child + 1]);
          }
        }

        Node calc(ptrdiff_t left, ptrdiff_t right) {
          left += (ptrdiff_t) size - 1;
          right += (ptrdiff_t) size - 2;
          leftAccumulator = NEUTRAL;
          rightAccumulator = NEUTRAL;
          while (left <= right) {
            if (left & 1) {
              left = (left - 1) >> 1;
            } else {
              leftAccumulator = merge(leftAccumulator, tree[left]);
              left = (left - 1) >> 1;
              ++left;
            }
            if (right & 1) {
              rightAccumulator = merge(rightAccumulator, tree[right]);
              right = (right - 1) >> 1;
              --right;
            } else {
              right = (right - 1) >> 1;
            }
          }
          return merge(leftAccumulator, rightAccumulator);
        }

        int get(int i) {
          return tree[size - 1 + i].value;
        }

        int find(int x, int l, int node, int left, int right) {
          if (tree[node].value < x or right < l) {
            return -1;
          }
          if (right == left + 1) {
            return left;
          }
          int middle = getMiddle(left, right);
          int child = (node << 1) + 1;
          int result = -1;
          if (tree[child].value >= x) {
            result = find(x, l, child, left, middle);
          }
          if (result == -1) {
            result = find(x, l, child + 1, middle, right);
          }
          return result;
        }

        int find(int x, int l) {
          return find(x, l, 0, 0, size);
        }

        void printTree() {
          for (auto el : tree) {
            cout << el.value << ' ';
          }
          cout << '\n';
        }
    };
}


template <typename T>
class Modular {
public:
    using Type = typename decay<decltype(T::value)>::type;

    constexpr Modular() : value() {}

    template <typename U>
    Modular(const U& x) {
      value = normalize(x);
    }

    template <typename U>
    static Type normalize(const U& x) {
      Type v = (-mod() <= x and x < mod()) ? static_cast<Type>(x) : static_cast<Type>(x % mod());
      if (v < 0) {
        v += mod();
      }
      return v;
    }

    const Type& operator()() const {
      return value;
    }

    template <typename U>
    explicit operator U() const {
      return static_cast<U>(value);
    }

    constexpr static Type mod() {
      return T::value; }

    Modular& operator+=(const Modular& other) {
      if ((value += other.value) >= mod()) {
        value -= mod();
      }
      return *this;
    }
private:
    Type value;
};

template <typename T> Modular<T> operator+(const Modular<T>& lhs, const Modular<T>& rhs) {
  return Modular<T>(lhs) += rhs;
}

template <typename T, typename U> Modular<T> operator+(const Modular<T>& lhs, U rhs) {
  return Modular<T>(lhs) += rhs;
}

template <typename T, typename U> Modular<T> operator+(U lhs, const Modular<T>& rhs) {
  return Modular<T>(lhs) += rhs;
}

template <typename U, typename T>
U& operator<<(U& stream, const Modular<T>& number) {
  return stream << number();
}

constexpr int MOD = 1000000007;
using Mint = Modular<std::integral_constant<decay<decltype(MOD)>::type, MOD>>;


namespace CHM {

    class UnionFind {
    public:
        int templates[300001][2];
        int lenght[300001];

        void init(int n) {
          for (int i = 0; i < n; i++) {
            templates[i + 1][0] = i + 1;
            templates[i + 1][1] = i + 1;
            lenght[i + 1] = 1;
          }
        }

        void unions(int a, int b) {
          if (templates[a][0] != templates[b][0] and templates[a][1] != templates[b][1]) {
            lenght[b] += lenght[a];
            if (templates[a][0] < templates[b][0]) {
              templates[b][0] = templates[a][0];
            }
            if (templates[a][1] > templates[b][1]) {
              templates[b][1] = templates[a][1];
            }
          }
        }

        void get(int a) {
          cout << templates[a][0] << ' ' << templates[a][1] << ' ' << lenght[a] << '\n';
        }
    };
}

class Solver {
private:
    void activation() {
      static bool start = false;
      if (!start) {
        srand(time(nullptr));
        start = true;
      }
    }

    size_t randomInRange(size_t left, size_t right) {
      activation();
      return left + rand() % ((right + 1) - left);
    }

    void solveOne() {
      typedef int itn;
      using namespace CHM;
      int n;
      cin >> n;
      UnionFind a{};
      a.init(n);
      int array[300001];
      for (int i = 0; i < n; i++) {
        array[i + 1] = i + 1;
      }
      string command;
      int representative1, representative2;
      int right, left, last;
      cin >> command;
      cin >> representative1;
      while (cin) {
        if (command == "union") {
          cin >> representative2;
          left = array[representative1];
          right = array[representative2];
          last = left;
          while (left != array[left]) {
            left = array[left];
            array[last] = array[left];
            last = array[left];
          }
          last = right;
          while (right != array[right]) {
            right = array[right];
            array[last] = array[right];
            last = array[right];
          }
          if (right < left) {
            a.unions(left, right);
            array[left] = right;
            array[representative2] = right;
          } else {
            a.unions(right, left);
            array[right] = left;
            array[representative1] = left;
          }
        } else {
          right = representative1;
          last = right;

          while (right != array[right]) {
            right = array[right];
            array[last] = array[right];
            last = array[right];
          }
          a.get(right);
        }
        cin >> command;
        cin >> representative1;
      }
    }
public:
    void solve() {
      int t = 1;
      // cin >> t;
      while (t--) {
        solveOne();
      }
    }
};

int main() {
  ios::sync_with_stdio(false);
  cin.tie(nullptr);
  //freopen("input.txt", "r", stdin);
  //freopen("problem3.out", "w", stdout);
  (new Solver)->solve();
  return 0;
}
