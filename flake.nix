# File 'flake.nix'.
{
  description = "Simple java project";
  nixConfig.bash-prompt = "[nix]λ. ";

  inputs.flake-utils.url = "github:numtide/flake-utils";
  inputs.nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
  outputs = { self, flake-utils, nixpkgs }:
    flake-utils.lib.eachDefaultSystem (system:
      let pkgs = nixpkgs.legacyPackages.${system}; in
      {
        devShells.default = with pkgs; mkShell {
          packages = [ jdk ];
          JAVA_HOME = "${jdk}/lib/openjdk";
        };
      }
    );
}
