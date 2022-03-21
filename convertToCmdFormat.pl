#!usr/bin/perl
use 5.10.1;
use warnings;
use strict;
use Cwd qw(cwd);
use feature qw(say);
use File::Copy::Recursive qw(fcopy rcopy dircopy fmove rmove dirmove);
use Archive::Extract;
use File::Path;

#read_pv_csv();
my $source_dir = cwd . "/src/com/company";
my $target_dir = cwd . "/cmdSrc";
my $lib_dir = cwd . "/lib";

if(-e $target_dir) {
	rmtree $target_dir;
}

mkdir ($target_dir) unless(-d $target_dir);

rcopy($lib_dir, $target_dir . "/lib") or die $!;

opendir(my $dir, $lib_dir) or die $!;
my @directory = readdir($dir);
my $src_lib_dir = $target_dir . "/lib/";
my @dirs;

foreach my $dir2 (@directory) {
  if($dir2 eq "." || $dir2 eq "..") { next; }
  my $new_dir = $src_lib_dir . $dir2 . "1";
  mkdir ($new_dir) unless(-d $new_dir);
  if($^O eq 'MSWin32') {
  	system "cd $src_lib_dir & tar -xf $dir2 -C $new_dir"; 
  } else {
  	system "cd $src_lib_dir && unzip -qq $dir2 -d $new_dir"; 
  }
  opendir(my $dir, $new_dir) or die $!;
  my @directory = readdir($dir);
  foreach my $dir5 (@directory) {
    if($dir5 eq "org" || $dir5 eq "javax") {
      rcopy($new_dir . "/" . $dir5, $target_dir . "/" . $dir5) or die $!;
    }
  }
}

closedir($dir);
rmtree $src_lib_dir;
rcopy($source_dir, $target_dir) or die $!;

my $java_files_ref = get_java_files($target_dir);
my @java_files = @{ $java_files_ref };

foreach my $file (@java_files) {
  my $data = read_java_file($file);
  if($file =~ /Main.java/) {
    $data =~ s/package com.company.//g;
    write_java_file($file, $data);
  }
    $data =~ s/com.company.//g;
  write_java_file($file, $data);
}

say "Finished successfuly, the program can now be compiled via the command line.";

exit 0;

sub get_java_files {
  opendir(my $dir, $_[0]) or die $!;
  my @directory = readdir($dir);
  my @endpoint_dirs;
  my @java_files;
  foreach my $dir2 (@directory) {
    if($dir2 eq "." || $dir2 eq "..") { next; }
    if($dir2 =~ /.java/) { 
      push(@java_files, $_[0]."/".$dir2); 
      next; 
    }
    push(@endpoint_dirs, $_[0]."/".$dir2);
  }
  for(my $i = 0; $i < scalar @endpoint_dirs; ++$i) {
    opendir(my $dir3, $endpoint_dirs[$i]) or die $!;
    while (my $file = readdir($dir3)) {
      next unless ($file =~ /.java/);
	    push(@java_files, $endpoint_dirs[$i]."/".$file);
    }
  }
  return \@java_files;
}

sub read_java_file {
  my ($filename) = @_;
 
    open my $in, '<:encoding(UTF-8)', $filename or die "Could not open '$filename' for reading $!";
    local $/ = undef;
    my $all = <$in>;
    close $in;
 
    return $all;
}

sub write_java_file {
    my ($filename, $content) = @_;
 
    open my $out, '>:encoding(UTF-8)', $filename or die "Could not open '$filename' for writing $!";;
    print $out $content;
    close $out;
 
    return;
}
