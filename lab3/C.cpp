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
#include <utility>
#include <vector>

#define int int32_t

typedef int64_t int64;
typedef uint64_t uint64;

using namespace std;

template <class T>
struct fenwick {
    int n;
    vector<T> fenw;

    fenwick(int n_) : n(n_), fenw(n_) {}

    void upd(int at, T delta) {
      for (; at < n; at |= at + 1) fenw[at] += delta;
    }

    T pref(int r) {
      T res{};
      for (; r >= 0; r = (r & (r + 1)) - 1) res += fenw[r];
      return res;
    }

    T get(int l, int r) {
      if (l >= r) return 0;
      return pref(r - 1) - pref(l - 1);
    }

    //  max i: pref(i-1) <= sum
    int lower_bound(T sum) {
      int pw = 1 << (int) log(n);
      int ans = 0;
      for (; pw > 0; pw /= 2) {
        if (ans + pw > n) {
          continue;
        }
        auto val = fenw[ans + pw - 1];
        if (sum >= val) {
          sum -= val;
          ans += pw;
        }
      }
      return ans;
    }
};

class Solver {
private:
    vector<int> result;

    void rec(const vector<int> &a, const vector<int> &b, const vector<vector<pair<int, int>>> &way, int i, int j) {
      if (!i and !j) {
        return;
      }
      if (a[i] == b[j]) {
        rec(a, b, way, i - 1, j - 1);
        result.push_back(a[i]);
      } else {
        if (way[i][j] == make_pair(i - 1, j)) {
          rec(a, b, way, i - 1, j);
        } else {
          rec(a, b, way, i, j - 1);
        }
      }
    }

    void solveOne() {
      int n;
      cin >> n;
      int m = n;
      vector<int> a(n + 1);
      vector<int> b(n + 1);
      for (int i = 1; i <= n; i++) {
        cin >> a[i];
        b[i] = a[i];
      }
      sort(b.begin(), b.end());
      vector<vector<int>> dp(n + 1, vector<int>(m + 1, 0));
      vector<vector<pair<int, int>>> way(n + 1, vector<pair<int, int>>(m + 1));
      // if a[i] == b[j] -> dp[i][j] = dp[i - 1][j - 1] + 1
      // else -> dp[i][j] = max(dp[i - 1][j], dp[i][j - 1])
      for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= m; j++) {
          if (a[i] == b[j]) {
            dp[i][j] = dp[i - 1][j - 1] + 1;
            way[i][j] = make_pair(i - 1, j - 1);
          } else {
            if (dp[i - 1][j] >= dp[i][j - 1]) {
              dp[i][j] = dp[i - 1][j];
              way[i][j] = make_pair(i - 1, j);
            } else {
              dp[i][j] = dp[i][j - 1];
              way[i][j] = make_pair(i, j - 1);
            }
          }
        }
      }
      rec(a, b, way, n, m);
      set<int> s;
      int count = 0;
      for (const auto el : result) {
        if (s.find(el) == s.end()) {
          s.insert(el);
          ++count;
        }
      }
      cout << count << '\n';
      s.clear();
      for (const auto el : result) {
        if (s.find(el) == s.end()) {
          s.insert(el);
          cout << el << ' ';
        }
      }
    }
public:
    void solve() {
      int t = 1;
#ifdef MultiTest
      cin >> t;
#endif
      while (t--) {
        solveOne();
      }
    }
};

int main() {
  ios_base::sync_with_stdio(false);
  cin.tie(nullptr);
#ifdef __llvm__
  freopen("input.txt", "r", stdin);
#endif
  (new Solver)->solve();
  return 0;
}

